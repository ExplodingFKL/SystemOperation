package tech.openEdgn.test.activities

import com.github.open_edgn.fx.manager.activity.FXMLActivity
import com.github.open_edgn.fx.manager.intent.Intent
import com.jfoenix.controls.JFXComboBox
import javafx.fxml.FXML
import javafx.scene.layout.VBox
import tech.openEdgn.test.system.memory.IMemoryAlgorithm
import tech.openEdgn.test.system.info.MemoryAlgorithmInfo
import tech.openEdgn.test.system.process.BaseProcessAlgorithm
import tech.openEdgn.test.system.info.ProcessAlgorithmInfo
import tech.openEdgn.test.system.memory.impl.BFMemoryAlgorithm
import tech.openEdgn.test.system.memory.impl.FFMemoryAlgorithm
import tech.openEdgn.test.system.memory.impl.NFMemoryAlgorithm
import tech.openEdgn.test.system.memory.impl.WFMemoryAlgorithm
import tech.openEdgn.test.system.process.impl.*


class StartActivity : FXMLActivity<VBox>() {

    @FXML
    private lateinit var process: JFXComboBox<ProcessAlgorithmInfo>

    @FXML
    private lateinit var memory: JFXComboBox<MemoryAlgorithmInfo>

    @FXML
    fun start() {
        startActivity(Intent(this, MainActivity::class)
                .putExtra(IMemoryAlgorithm::class.simpleName!!, memory.selectionModel.selectedItem.implClass.java)
                .putExtra(BaseProcessAlgorithm::class.simpleName!!, process.selectionModel.selectedItem.implClass.java)
                .putExtra(IMemoryAlgorithm::class.qualifiedName!!, memory.selectionModel.selectedItem.name)
                .putExtra(BaseProcessAlgorithm::class.qualifiedName!!, process.selectionModel.selectedItem.name)
        )
    }

    override fun onCreate() {
        process.items.addAll(
                ProcessAlgorithmInfo("先来先服务调度算法", FCFSAlgorithm::class),
                ProcessAlgorithmInfo("时间片轮转调度算法", RRAlgorithm::class),
                ProcessAlgorithmInfo("高响应比优先调度算法", HRRNAlgorithm::class),
                ProcessAlgorithmInfo("优先级调度算法", SJFAlgorithm::class),
                ProcessAlgorithmInfo("多级反馈队列调度算法", MFQAlgorithm::class)
        )
        memory.items.addAll(
                MemoryAlgorithmInfo("首次适应算法", FFMemoryAlgorithm::class),
                MemoryAlgorithmInfo("循环适应算法", NFMemoryAlgorithm::class),
                MemoryAlgorithmInfo("最佳适应算法", BFMemoryAlgorithm::class),
                MemoryAlgorithmInfo("最坏适应算法", WFMemoryAlgorithm::class)
        )
        process.selectionModel.select(0)
        memory.selectionModel.select(0)
    }

    override val fxmlPath: String = "/fxml/activity_start.fxml"
    override val title: String = "选择调度算法"
    override val iconPath: String = "/icons/main.png"
}

