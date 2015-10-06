-- 26.08.2015 17:59:34
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552691,53355,0,19,390,'N','C_BankStatementLine_Ref_ID',' (SELECT C_BankStatementLine_Ref_ID FROM C_BankStatementLine_Ref bslr  left outer join C_BankStatementLine bsl on (bsl.C_BankStatementLine_ID=bslr.C_BankStatementLine_ID) left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) WHERE bslr.C_Payment_ID=C_AllocationLine.C_Payment_ID and bs.DocStatus IN (''CO'', ''CL''))',TO_TIMESTAMP('2015-08-26 17:59:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Bankstatementline Reference',0,TO_TIMESTAMP('2015-08-26 17:59:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 26.08.2015 17:59:34
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552691 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.08.2015 18:00:10
-- URL zum Konzept
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2015-08-26 18:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552691
;

-- 26.08.2015 18:02:04
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552692,1382,0,19,390,'N','C_BankStatementLine_ID','(SELECT C_BankStatementLine_ID FROM C_BankStatementLine bsl  left outer join C_BankStatement bs on (bs.C_BankStatement_ID=bsl.C_BankStatement_ID) WHERE bsl.C_Payment_ID=C_AllocationLine.C_Payment_ID and bs.DocStatus IN (''CO'', ''CL''))',TO_TIMESTAMP('2015-08-26 18:02:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Position auf einem Bankauszug zu dieser Bank','D',10,'Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.','Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Auszugs-Position',0,TO_TIMESTAMP('2015-08-26 18:02:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 26.08.2015 18:02:04
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552692 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.08.2015 18:03:36
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552692,556274,0,349,0,TO_TIMESTAMP('2015-08-26 18:03:36','YYYY-MM-DD HH24:MI:SS'),100,'Position auf einem Bankauszug zu dieser Bank',0,'de.metas.swat','Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.',0,'Y','Y','Y','Y','N','N','N','N','N','Auszugs-Position',150,150,0,TO_TIMESTAMP('2015-08-26 18:03:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2015 18:03:36
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556274 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 26.08.2015 18:04:04
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.swat',Updated=TO_TIMESTAMP('2015-08-26 18:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556274
;

-- 26.08.2015 18:04:22
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552691,556275,0,349,0,TO_TIMESTAMP('2015-08-26 18:04:22','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.swat',0,'Y','Y','Y','Y','N','N','N','N','Y','Bankstatementline Reference',160,160,0,TO_TIMESTAMP('2015-08-26 18:04:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.08.2015 18:04:22
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556275 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

