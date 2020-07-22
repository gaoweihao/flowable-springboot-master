package com.flowable.springboot.cascade;

import org.flowable.common.engine.impl.AbstractEngineConfiguration;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;

public class DeleteProcessInstanceCascadeCmd implements Command<Void> {
    private String processInstanceId = null;
    private String deleteReason = "流程删除";

    public DeleteProcessInstanceCascadeCmd(String processInstanceId, String deleteReason) {
        this.processInstanceId = processInstanceId;
        this.deleteReason = deleteReason;
    }

    @Override
    public Void execute(CommandContext commandContext) {
        AbstractEngineConfiguration currentEngineConfiguration = commandContext.getCurrentEngineConfiguration();
        if (null != currentEngineConfiguration) {
            ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) currentEngineConfiguration;
            processEngineConfiguration.getExecutionEntityManager().deleteProcessInstance(processInstanceId, deleteReason, true);
        }
        return null;
    }
}
