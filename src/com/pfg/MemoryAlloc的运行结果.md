### testAllocation的运行结果
参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8  
jdk版本:1.8.0_162
```
public static void testAllocation() {
		byte [] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB]; // 这里并没有发生Minor GC
	}
  
结果：  
前面3个2M都分配到了新生代中的Eden区，而后面4M被认为是大对象直接分配到了老年代  
Heap
 PSYoungGen      total 9216K, used 7457K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 91% used [0x00000000ff600000,0x00000000ffd486e8,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
 ParOldGen       total 10240K, used 4096K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 40% used [0x00000000fec00000,0x00000000ff000010,0x00000000ff600000)
 Metaspace       used 2785K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
```
而  
```
public static void testAllocation() {
		byte [] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[3 * _1MB];  
	}
  
结果：  
3M不被认为是大对象，所以还是分配到新生代中的Eden区，但是这时新生代容量不够了(Eden+Survivor0区应该小于9M，而且Survivor1空间只有1M)，会触发GC，但是allocation1、allocation2和allocation3  
作为GC Root是需要存活的，这时Survivor1空间只有1M所以只能通过分配担保机制提前转移到老年代中(即6772k),  
所以新生代中又有分配3M的空间了。
[GC (Allocation Failure) [PSYoungGen: 7293K->760K(9216K)] 7293K->6912K(19456K), 0.0040521 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Ergonomics) [PSYoungGen: 760K->0K(9216K)] [ParOldGen: 6152K->6772K(10240K)] 6912K->6772K(19456K), [Metaspace: 2783K->2783K(1056768K)], 0.0074571 secs] [Times: user=0.05 sys=0.00, real=0.01 secs] 
Heap
 PSYoungGen      total 9216K, used 3154K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 38% used [0x00000000ff600000,0x00000000ff914930,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 6772K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 66% used [0x00000000fec00000,0x00000000ff29d090,0x00000000ff600000)
 Metaspace       used 2789K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 298K, capacity 386K, committed 512K, reserved 1048576K
```
