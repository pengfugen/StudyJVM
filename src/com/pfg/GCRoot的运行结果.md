### 参数
-verbose:gc -Xms200M -Xmx200M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -Xss128k -XX:+HeapDumpOnOutOfMemoryError  
或  
-verbose:gc -Xms100M -Xmx100M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -Xss128k -XX:+HeapDumpOnOutOfMemoryError  
遇到的问题：  
因为例子中有80M的内存要分配，-Xmn100M新生代指定100M，若-Xms和-Xmx小于180M就会发生(OutOfMemoryError: Java heap space)，说明80M是分配到老生代中。
### 注意
由MemoryAlloc验证结果可知80M肯定是在老年代中分配

### GC Root的第一种情况的结果：  
```
method1调用System.gc：  
[GC (System.gc()) [PSYoungGen: 3276K->768K(92160K)] 85196K->82696K(194560K), 0.0017456 secs] [Times: user=0.01 sys=0.00, real=0.00 secs] 
[Full GC (System.gc()) [PSYoungGen: 768K->0K(92160K)] [ParOldGen: 81928K->82548K(102400K)] 82696K->82548K(194560K), [Metaspace: 2779K->2779K(1056768K)], 0.0070863 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 

main方法：  
//新生代没有可回收的资源  
[GC (System.gc()) [PSYoungGen: 0K->0K(92160K)] 82548K->82548K(194560K), 0.0009370 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
// 老生代回收80M((82548K-628k)/1024)
// 为什么存在老生代中？
[Full GC (System.gc()) [PSYoungGen: 0K->0K(92160K)] [ParOldGen: 82548K->628K(102400K)] 82548K->628K(194560K), [Metaspace: 2779K->2779K(1056768K)], 0.0069279 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 92160K, used 819K [0x00000000f9c00000, 0x0000000100000000, 0x0000000100000000)
  eden space 81920K, 1% used [0x00000000f9c00000,0x00000000f9ccce40,0x00000000fec00000)
  from space 10240K, 0% used [0x00000000ff600000,0x00000000ff600000,0x0000000100000000)
  to   space 10240K, 0% used [0x00000000fec00000,0x00000000fec00000,0x00000000ff600000)
 ParOldGen       total 102400K, used 628K [0x00000000f3800000, 0x00000000f9c00000, 0x00000000f9c00000)
  object space 102400K, 0% used [0x00000000f3800000,0x00000000f389d040,0x00000000f9c00000)
 Metaspace       used 2786K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
```
### GC Root第二种情况的运行结果：
```
[GC (System.gc()) [PSYoungGen: 1149K->768K(9216K)] 42109K->41736K(203776K), 0.0015844 secs] [Times: user=0.00 sys=0.01, real=0.00 secs] 
// root = null;然后调用System.gc()回收了40M资源
[Full GC (System.gc()) [PSYoungGen: 768K->0K(9216K)] [ParOldGen: 40968K->628K(194560K)] 41736K->628K(203776K), [Metaspace: 2778K->2778K(1056768K)], 0.0095457 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 82K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 1% used [0x00000000ff600000,0x00000000ff614930,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 194560K, used 82548K [0x00000000f3800000, 0x00000000ff600000, 0x00000000ff600000)
  object space 194560K, 42% used [0x00000000f3800000,0x00000000f889d050,0x00000000ff600000)
 Metaspace       used 2785K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
```
### GC Root第三种情况的运行结果：
```
[GC (System.gc()) [PSYoungGen: 1149K->712K(9216K)] 124029K->123600K(203776K), 0.0015286 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
// 回收了40M
[Full GC (System.gc()) [PSYoungGen: 712K->0K(9216K)] [ParOldGen: 122888K->82548K(194560K)] 123600K->82548K(203776K), [Metaspace: 2778K->2778K(1056768K)], 0.0070547 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 82K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 1% used [0x00000000ff600000,0x00000000ff614920,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 194560K, used 82548K [0x00000000f3800000, 0x00000000ff600000, 0x00000000ff600000)
  object space 194560K, 42% used [0x00000000f3800000,0x00000000f889d068,0x00000000ff600000)
 Metaspace       used 2785K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
```
#### 问题
 // GC Root的第三种情况  
 private final static GCRoot root3 = new GCRoot(80);  
 若上述代码不加static即：  
 private final GCRoot root3 = new GCRoot(80);  
 出现如下错误： 
 ```
 Exception in thread "main" java.lang.StackOverflowError
	at com.pfg.GCRoot.<init>(GCRoot.java:15)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
	at com.pfg.GCRoot.<init>(GCRoot.java:29)
 ```
为什么会出现栈错误？root3不是分配在方法区吗？
