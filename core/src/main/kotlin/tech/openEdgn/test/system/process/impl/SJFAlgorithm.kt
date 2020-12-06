package tech.openEdgn.test.system.process.impl

import tech.openEdgn.test.system.ProcessStatus
import tech.openEdgn.test.system.manager.ISystemManager
import tech.openEdgn.test.system.memory.IMemoryAlgorithm
import tech.openEdgn.test.system.pcbs.SJFPCB
import tech.openEdgn.test.system.process.CustomAlgorithm
import java.util.*
import kotlin.math.abs

class SJFAlgorithm(memoryAlgorithm: IMemoryAlgorithm, manager: ISystemManager) :
        CustomAlgorithm<SJFPCB>(memoryAlgorithm, manager) {
    override fun runClockCycle() {
        // 清空就绪队列下的非就绪进程
        clearUnReadyProcess()
        // 将符合条件的进程添加到就绪队列下
        addReadyProcess()
        // 选取运行进程
        readyProcess.sortByDescending { it.priority }
        // 排序高优先比
        if (runProcess.isEmpty() && readyProcess.isNotEmpty()) {
            readyProcess.first.status = ProcessStatus.RUN
        }
        if (runProcess.isNotEmpty()) {
            if (runProcess.first() != readyProcess.first) {
                // 此时运行的进程优先比非最高
                readyProcess.first.status = ProcessStatus.RUN
                runProcess.first().status = ProcessStatus.RUN_READY
            }
        }
        val process = runProcess
        if (process.isNotEmpty()) {
            process.first().run {
                usedCpuTime++
            }
        }

        // 移除运行完成的进程
        removeFinishedProcess()
    }

    override fun addRandomProcess() {
        allProcesses.add(
                SJFPCB(name = "进程 ${System.currentTimeMillis() % 1000}",
                        startTime = manager.runTime,
                        needTime = abs(abs(Random().nextLong()) % 8 + 3),
                        priority = abs(abs(Random().nextInt()) % 10 + 1),
                        needMemory = abs(abs(Random().nextInt() % 80) + 30)
                )
        )

    }

    override val displayClass = SJFPCB::class
}