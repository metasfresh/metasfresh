-- 2021-11-09T20:03:22.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:03:54.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2021-11-09 22:03:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:04:06.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:04:41.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2021-11-09 22:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:12:21.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2021-11-09T20:12:42.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:12:53.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:12:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2021-11-09T20:13:02.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-11-09 22:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2021-11-09T20:14:16.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2021-11-09 22:14:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2021-11-09T20:14:32.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=183,Updated=TO_TIMESTAMP('2021-11-09 22:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2021-11-09T20:14:49.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2021-11-09 22:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:14:59.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=167,Updated=TO_TIMESTAMP('2021-11-09 22:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2021-11-09T20:15:34.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''Y'' )',Updated=TO_TIMESTAMP('2021-11-09 22:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2021-11-09T20:15:46.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''Y''  )',Updated=TO_TIMESTAMP('2021-11-09 22:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:15:59.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''N'' )',Updated=TO_TIMESTAMP('2021-11-09 22:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2021-11-09T20:16:11.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''Y'' )',Updated=TO_TIMESTAMP('2021-11-09 22:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2021-11-09T20:16:16.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''N'' )',Updated=TO_TIMESTAMP('2021-11-09 22:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;










-- 2021-11-09T20:18:53.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540739,540742,540330,TO_TIMESTAMP('2021-11-09 22:18:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Invoice (SO)-> Reference Invoice (PO)',TO_TIMESTAMP('2021-11-09 22:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-09T20:19:13.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice (PO)  Reference target for C_Invoice',Updated=TO_TIMESTAMP('2021-11-09 22:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2021-11-09T20:21:17.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice (SO) target for Reference Invoice',Updated=TO_TIMESTAMP('2021-11-09 22:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2021-11-09T20:21:21.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540744,540741,540331,TO_TIMESTAMP('2021-11-09 22:21:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Referenced Invoice (PO) -> Invoice (SO)',TO_TIMESTAMP('2021-11-09 22:21:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-09T20:21:58.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice (PO) Reference target for C_Invoice',Updated=TO_TIMESTAMP('2021-11-09 22:21:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2021-11-09T20:22:28.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice (PO)  target for Reference Invoice',Updated=TO_TIMESTAMP('2021-11-09 22:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540743
;

-- 2021-11-09T20:23:13.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Invoice (SO) -> Referenced Invoice (SO)',Updated=TO_TIMESTAMP('2021-11-09 22:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540184
;

-- 2021-11-09T20:23:27.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Invoice (SO) Reference target for C_Invoice',Updated=TO_TIMESTAMP('2021-11-09 22:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540740
;

-- 2021-11-09T20:23:51.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Invoice (PO)-> Referenced Invoice PO',Updated=TO_TIMESTAMP('2021-11-09 22:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540186
;

-- 2021-11-09T20:24:04.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='Referenced Invoice (PO)-> Invoice (PO)',Updated=TO_TIMESTAMP('2021-11-09 22:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540187
;

-- 2021-11-09T20:24:15.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='Referenced Invoice (SO)  -> Invoice (SO)',Updated=TO_TIMESTAMP('2021-11-09 22:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540185
;

