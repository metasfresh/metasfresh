-- 20.11.2015 16:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsExplicit,Name,Updated,UpdatedBy) VALUES (0,0,540143,TO_TIMESTAMP('2015-11-20 16:52:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','AD_Role_Included -> AD_Role',TO_TIMESTAMP('2015-11-20 16:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.11.2015 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540613,TO_TIMESTAMP('2015-11-20 16:54:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','AD_Role_Included -> AD_Role',TO_TIMESTAMP('2015-11-20 16:54:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 20.11.2015 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540613 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 20.11.2015 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Shows which are the roles that include the current role.',Updated=TO_TIMESTAMP('2015-11-20 16:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540613
;

-- 20.11.2015 16:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540613
;

-- 20.11.2015 16:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,531,0,540613,156,111,TO_TIMESTAMP('2015-11-20 16:55:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-20 16:55:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.11.2015 18:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='

exists
(
	select 1 from AD_Role parent_role 
	join AD_Role_Included included_role on parent_role.AD_Role_ID = included_role.AD_Role_ID and parent_role.isActive = ''Y'' and included_role.isActive = ''Y''
	join AD_Role child_role on included_role.Included_Role_ID = child_role.AD_Role_ID and child_role.isActive = ''Y''
	
	where
		AD_Role.AD_Role_ID = child_role.AD_Role_ID and
		(child_role.AD_Role_ID = @AD_Role_ID/-1@ or parent_role.AD_Role_ID = @AD_Role_ID/-1@  )
)',Updated=TO_TIMESTAMP('2015-11-20 18:11:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540613
;

-- 20.11.2015 18:24
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540613,Updated=TO_TIMESTAMP('2015-11-20 18:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540143
;

-- 20.11.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540614,TO_TIMESTAMP('2015-11-20 18:25:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','RelType AD_Role_Included -> AD_Role',TO_TIMESTAMP('2015-11-20 18:25:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 20.11.2015 18:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540614 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 20.11.2015 18:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,531,0,540614,156,111,TO_TIMESTAMP('2015-11-20 18:26:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N',TO_TIMESTAMP('2015-11-20 18:26:35','YYYY-MM-DD HH24:MI:SS'),100,'

exists
(
	select 1 from AD_Role parent_role 
	join AD_Role_Included included_role on parent_role.AD_Role_ID = included_role.AD_Role_ID and parent_role.isActive = ''Y'' and included_role.isActive = ''Y''
	join AD_Role child_role on included_role.Included_Role_ID = child_role.AD_Role_ID and child_role.isActive = ''Y''
	
	where
		AD_Role.AD_Role_ID = parent_role.AD_Role_ID and
		(child_role.AD_Role_ID = @AD_Role_ID/-1@ or parent_role.AD_Role_ID = @AD_Role_ID/-1@  )
)')
;

-- 20.11.2015 18:26
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540614,Updated=TO_TIMESTAMP('2015-11-20 18:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540143
;

