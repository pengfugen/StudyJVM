
### case 4的执行结果：
```
jdk1.8
[GC (System.gc()) [PSYoungGen: 5245K->696K(9216K)] 5245K->704K(19456K), 0.0013247 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (System.gc()) [PSYoungGen: 696K->0K(9216K)] [ParOldGen: 8K->628K(10240K)] 704K->628K(19456K), [Metaspace: 2781K->2781K(1056768K)], 0.0178660 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Heap
 PSYoungGen      total 9216K, used 82K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 1% used [0x00000000ff600000,0x00000000ff614920,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 628K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 6% used [0x00000000fec00000,0x00000000fec9d190,0x00000000ff600000)
 Metaspace       used 2788K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0
注意：  
jdk1.8的堆区分为三部分：  
PSYoungGen  
ParOldGen  
Metaspace  
testGC中的System.gc回收了如下资源：  
[GC (System.gc()) [PSYoungGen: 5245K->696K(9216K)] 5245K->704K(19456K), 0.0013247 secs]
```
