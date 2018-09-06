```
[GC (System.gc()) [PSYoungGen: 1149K->776K(9216K)] 1149K->784K(19456K), 0.0950881 secs] [Times: user=0.00 sys=0.00, real=0.10 secs] 
[Full GC (System.gc()) [PSYoungGen: 776K->0K(9216K)] [ParOldGen: 8K->628K(10240K)] 784K->628K(19456K), [Metaspace: 2780K->2780K(1056768K)], 0.0050244 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
finalize method executed!
yes, i am still alive
[GC (System.gc()) [PSYoungGen: 327K->32K(9216K)] 956K->660K(19456K), 0.0003089 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (System.gc()) [PSYoungGen: 32K->0K(9216K)] [ParOldGen: 628K->627K(10240K)] 660K->627K(19456K), [Metaspace: 2781K->2781K(1056768K)], 0.0078924 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
no, iam dead
Heap
 PSYoungGen      total 9216K, used 246K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 3% used [0x00000000ff600000,0x00000000ff63d890,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 627K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 6% used [0x00000000fec00000,0x00000000fec9cef0,0x00000000ff600000)
 Metaspace       used 2788K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0

```
