package tech.openEdgn.test.system

interface PCB {
    /**
     *  pid
     */
    var pid: Long

    /**
     *  process name
     */
    var name: String

    /**
     * process status
     */
    var status: ProcessStatus

    /**
     *  进程开始执行的时间
     */
    val startTime: Long

    /**
     * 需要的时间
     */
    val needTime: Long

    /**
     * 需要的内存数目
     */
    val needMemory:Int

    /**
     * 内存首地址
     */
    var memoryOffset: Int

    /**
     * 已用cpu时间
     */
    var usedCpuTime: Long
}
