package com.javap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.io.IOException;;;

public class JavapTask {
	private static int offset = 0;
	
	//access flags
	private static final int ACC_PUBLIC     = 0x0001;
	private static final int ACC_FINAL      = 0x0010;
	private static final int ACC_SUPER      = 0x0020;
	private static final int ACC_INTERFACE  = 0x0200;
	private static final int ACC_ABSTRACT   = 0x0400;
	
	//field access flags
	private static final int FIELD_ACC_PUBLIC     = 0x0001;
	private static final int FIELD_ACC_PRIVATE    = 0x0002;
	private static final int FIELD_ACC_PROTECTED  = 0x0004;
	private static final int FIELD_ACC_STATIC     = 0x0008;
	private static final int FIELD_ACC_FINAL      = 0x0010;
	private static final int FIELD_ACC_VOLATILE   = 0x0040;
	private static final int FIELD_ACC_TRANSIENT  = 0x0080;
	private static final int FIELD_ACC_SYNTHETIC  = 0x1000;
	private static final int FIELD_ACC_ENUM       = 0x4000;
	
	private Map<Integer, String> utf8_map = new HashMap<Integer, String>();
	
	public void run(String arg) {
		String content = "";
		try {
			File file = new File(arg);
			FileInputStream fis = new FileInputStream(file);
			byte[] filecontent = new byte[(int)file.length()];
			try {
				fis.read(filecontent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("1==e:"+e.getMessage());
				e.printStackTrace();
			}
			fis.close();
			
			content = bytesToHexString(filecontent);
			System.out.println(content);
			handleContent(content);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("2==e:"+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("3==e:"+e.getMessage());
		}
		
	}
	
	private void handleContent(String content) {
		
		String magic = content.substring(offset, offset+8);
		offset = offset + 8;
		System.out.println(magic);
		String minor_version = content.substring(offset, offset + 4);
		offset = offset + 4;
		String major_version = content.substring(offset, offset + 4);
		offset = offset + 4;
		StringBuffer sb = new StringBuffer();
		sb.append("minor version:").append(Integer.parseInt(minor_version, 16)).append("\n");
		sb.append("major version:").append(Integer.parseInt(major_version, 16)).append("\n");
		sb.append("flags:");
		System.out.println(sb.toString());
		int constat_pool_count = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		System.out.println("constat_pool_count:"+(constat_pool_count-1));
		
		// parse constant_pool_info
		for(int i = 0; i < (constat_pool_count-1); i++) {
			int tag = Integer.parseInt(content.substring(offset, offset + 2), 16);
			offset = offset + 2;
			//System.out.println("before offset:"+offset);
			//System.out.println("tag:"+tag);
			switch(tag) {
			case 1:
				getCONSTANT_Utf8_Info(i+1, tag, content);
				break;
			case 7:
				getCONSTANT_Class_Info(i+1, tag, content);
				break;
			case 10:
				getCONSTANT_Methodref_Info(i+1, tag, content);
				break;
			case 12:
				getCONSTANT_NameAndType_Info(i+1, tag, content);
				break;
			default:
				System.out.println("not process tag:"+tag + " i:"+i);
				break;
			}
			//System.out.println("after offset:"+offset);
		}
		int access_flags = Integer.parseInt(content.substring(offset, offset + 4), 16);
		//System.out.println("access_flags:"+access_flags);
		offset = offset + 4;
		
		System.out.println(getAccessFlags(access_flags));
		
		//访问标识之后就是类索引(u2)和父类(u2)以及接口索引集合(第一项为接口计算器(u2)interfaces_count，表示索引表的容量)
		int class_index = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		int super_index = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		//正常的话这里有循环处理的
		int interfaces_count = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		System.out.println("class_index:"+class_index+", super_index:"+super_index+", interfaces_count:"+interfaces_count);
		StringBuffer sb1 = new StringBuffer();
		sb1.append("{\n");
		//有多个filed的话应该有循环处理
		int fields_count = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		int fields_access_flags;
		int name_index;
		int descriptor_index;
		int unknow;
		for(int i = 0; i < fields_count; i++) {
			fields_access_flags = Integer.parseInt(content.substring(offset, offset + 4), 16);
			offset = offset + 4;
			name_index = Integer.parseInt(content.substring(offset, offset + 4), 16);
			offset = offset + 4;
			descriptor_index = Integer.parseInt(content.substring(offset, offset + 4), 16);
			offset = offset + 4;
			unknow = Integer.parseInt(content.substring(offset, offset + 4), 16);
			offset = offset + 4;
			sb1.append(" ").append(getFieldAccessFlags(fields_access_flags)).append(" ").append(getDescriptor(utf8_map.get(descriptor_index))).append(" ").append(utf8_map.get(name_index)).append(";");
			sb1.append("\n    ").append("descriptor: ").append(utf8_map.get(descriptor_index));
			sb1.append("\n    ").append("flags: ").append(getFieldAccFlagsDesc(fields_access_flags));
			sb1.append("\n\n");
		}
		
		System.out.println(sb1.toString());
		
		
	}
	
	private StringBuffer getAccessFlags(int accflags) {
		//System.out.println("access_flags:"+accflags);
		
		StringBuffer aflags = new StringBuffer();
		aflags.append("flags: ");
		if((accflags&ACC_PUBLIC) != 0) {
			aflags.append("ACC_PUBLIC, ");
		}
		if((accflags&ACC_SUPER) != 0) {
			aflags.append("ACC_SUPER");
		}
		return aflags;
	}
	
	private StringBuffer getFieldAccessFlags(int accflags) {
		//System.out.println("access_flags:"+accflags);
		
		StringBuffer aflags = new StringBuffer();
		// 三个互斥
		if((accflags&FIELD_ACC_PUBLIC) != 0) {
			aflags.append("public");
		} else if((accflags&FIELD_ACC_PRIVATE) != 0) {
			aflags.append("private");
		} else if((accflags&FIELD_ACC_PROTECTED) != 0) {
			aflags.append("protected");
		}
		
		return aflags;
	}
	
	private StringBuffer getFieldAccFlagsDesc(int accflags) {
		//System.out.println("access_flags:"+accflags);
		
		StringBuffer aflags = new StringBuffer();
		// 三个互斥
		if((accflags&FIELD_ACC_PUBLIC) != 0) {
			aflags.append("ACC_PUBLIC");
		} else if((accflags&FIELD_ACC_PRIVATE) != 0) {
			aflags.append("ACC_PRIVATE");
		} else if((accflags&FIELD_ACC_PROTECTED) != 0) {
			aflags.append("ACC_PROTECTED");
		}
		
		return aflags;
	}
	
	private String getDescriptor(String desc) {
		if(desc.equals("I")) {
			return "int";
		} else if(desc.equals("C")) {
			return "char";
		}
		return "";
	}
	
	private static void getCONSTANT_Class_Info(int index, int tag, String content) {
		// tag=7, CONSTANT_Class_Info
		// CONSTANT_Class_Info{u1 tag, u2 name_index}
		int name_index = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		String ss = "";
		if(index>9) {
			ss = "#"+index;
		} else {
			ss = " #"+index;
		}
		System.out.println(ss+" = CONSTANT_Class_Info:"+tag+",#"+name_index);
	}
	
	private void getCONSTANT_Utf8_Info(int index, int tag, String content) {
		// tag = 1, CONSTANT_Utf8_Info
		// CONSTANT_Utf8_Info{u1 tag, u2 length, u1 bytes}
		int length = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		String bytes = content.substring(offset, offset + length*2);
		//System.out.println("#1 bytes:"+bytes);
		offset = offset + length*2;
		try {
			String ss = "";
			if(index>9) {
				ss = "#"+index;
			} else {
				ss = " #"+index;
			}
			String value = hex2String(bytes);
			System.out.println(ss +" = CONSTANT_Utf8_Info:"+tag+","+length+","+value);
			utf8_map.put(index, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void getCONSTANT_Methodref_Info(int index, int tag, String content) {
		// tag=10, CONSTANT_Methodref_Info
		// CONSTANT_Methodref_Info{u1 tag, u2 index, u2 index}
		int name_index1 = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		int name_index2 = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		String ss = "";
		if(index>9) {
			ss = "#"+index;
		} else {
			ss = " #"+index;
		}
		System.out.println(ss+" = CONSTANT_Methodref_Info:"+tag+",#"+name_index1+".#"+name_index2);
	}
	
	private void getCONSTANT_NameAndType_Info(int index, int tag, String content) {
		// tag=12, CONSTANT_NameAndType_Info
		// CONSTANT_NameAndType_Info{u1 tag, u2 index, u2 index}
		int name_index1 = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		int name_index2 = Integer.parseInt(content.substring(offset, offset + 4), 16);
		offset = offset + 4;
		String ss = "";
		if(index>9) {
			ss = "#"+index;
		} else {
			ss = " #"+index;
		}
		System.out.println(ss+" = CONSTANT_NameAndType_Info:"+tag+",#"+name_index1+":#"+name_index2);
	}
	
	public String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	 
	public byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	
	public String hex2String(String hex) throws Exception{
        String r = bytes2String(hexString2Bytes(hex));        
        return r;
    }
	
	public String bytes2String(byte[] b) throws Exception {
        String r = new String (b,"UTF-8");        
        return r;
    }
	
	public byte[] hexString2Bytes(String hex) {

		if ((hex == null) || (hex.equals(""))) {
			return null;
		} else if (hex.length() % 2 != 0) {
			return null;
		} else {
			hex = hex.toUpperCase();
			int len = hex.length() / 2;
			byte[] b = new byte[len];
			char[] hc = hex.toCharArray();
			for (int i = 0; i < len; i++) {
				int p = 2 * i;
				b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
			}
			return b;
		}

	}
	
	private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
     }
	
	private char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}
}
