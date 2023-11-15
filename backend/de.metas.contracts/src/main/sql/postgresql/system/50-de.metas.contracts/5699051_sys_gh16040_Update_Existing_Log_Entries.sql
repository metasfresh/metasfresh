-- up to this point, we were only dealing with `ModularContract` logs
update ModCntr_Log set ContractType='ModularContract', Updated=TO_TIMESTAMP('2023-08-14 15:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=99 where ContractType is NULL
;