package tech.openEdgn.test.system.memory.impl

import com.github.openEdgn.logger4k.getLogger
import tech.openEdgn.test.system.PCB
import tech.openEdgn.test.system.memory.IMemoryAlgorithm

class MaxMemoryAlgorithm : IMemoryAlgorithm {
    private val logger = getLogger()

    @Volatile
    override var memoryUsageSize: Int = 0
    override val memorySize: Int
        get() = 8192

    private val freeSize: Int
        get() = memorySize - memoryUsageSize

    @Synchronized
    override fun finishProcess(process: PCB) {
        process.memoryOffset = 0
        memoryUsageSize -= process.needMemory
    }

    @Synchronized
    override fun tryBootProcess(process: PCB): Boolean {
        return if (freeSize < process.needMemory) {
            logger.info("系统可用内存不足 [可用：{} < 需要：{}]，进程 [{}] 无法启动.",
                    freeSize, process.needMemory, process.name)
            false
        } else {
            memoryUsageSize += process.needMemory
            process.memoryOffset = memoryUsageSize
            logger.info("进程 {} 需要内存小于系统已用内存，可以启动.",process.name)
            true
        }
    }
}