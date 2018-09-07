Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded  
意思是程序申请的内存过大导致GC一直在运行以至于程序无法继续执行所引发的问题。  
执行结果如下：  
```
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10041K->10041K(10240K)] 18233K->18233K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1236539 secs] [Times: user=0.45 sys=0.00, real=0.12 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10042K->10042K(10240K)] 18234K->18234K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1320407 secs] [Times: user=0.44 sys=0.00, real=0.13 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10043K->10043K(10240K)] 18235K->18235K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1264920 secs] [Times: user=0.44 sys=0.02, real=0.13 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10044K->10044K(10240K)] 18236K->18236K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1361649 secs] [Times: user=0.42 sys=0.00, real=0.14 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10045K->10045K(10240K)] 18237K->18237K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1476159 secs] [Times: user=0.44 sys=0.00, real=0.15 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10046K->10046K(10240K)] 18238K->18238K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1472992 secs] [Times: user=0.45 sys=0.00, real=0.15 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10047K->10047K(10240K)] 18239K->18239K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1384377 secs] [Times: user=0.41 sys=0.03, real=0.14 secs] 
[Full GC (Ergonomics) [PSYoungGen: 8191K->8191K(9216K)] [ParOldGen: 10048K->10048K(10240K)] 18240K->18240K(19456K), [Metaspace: 2790K->2790K(1056768K)], 0.1263132 secs] [Times: user=0.34 sys=0.02, real=0.13 secs] 
java.lang.OutOfMemoryError: GC overhead limit exceeded
Dumping heap to java_pid13220.hprof ...
Heap dump file created [31546278 bytes in 0.131 secs]
Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
[Full GC (Ergonomics) [PSYoungGen: 8191K->0K(9216K)] [ParOldGen: 10051K->617K(10240K)] 18243K->617K(19456K), [Metaspace: 2815K->2815K(1056768K)], 0.0096278 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	at com.pfg.Stack.main(Stack.java:43)
Heap
 PSYoungGen      total 9216K, used 164K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 2% used [0x00000000ff600000,0x00000000ff629190,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 617K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 6% used [0x00000000fec00000,0x00000000fec9a400,0x00000000ff600000)
 Metaspace       used 2822K, capacity 4490K, committed 4864K, reserved 1056768K
  class space    used 302K, capacity 386K, committed 512K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0
```
