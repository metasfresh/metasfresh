-- 27.08.2015 11:48:16
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552693,2534,0,19,393,'N','C_AllocationLine_ID','(select al.C_AllocationLine_ID from C_AllocationLine al inner join C_Payment p on (p.C_Payment_ID=al.C_Payment_ID) where C_BankStatementLine.C_Payment_ID=p.C_Payment_ID) ',TO_TIMESTAMP('2015-08-27 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Zuordnungs-Position','D',10,'Allocation of Cash/Payment to Invoice','Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Zuordnungs-Position',0,TO_TIMESTAMP('2015-08-27 11:48:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 27.08.2015 11:48:16
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552693 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 27.08.2015 11:48:42
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552694,2534,0,19,53065,'N','C_AllocationLine_ID',' (select al.C_AllocationLine_ID from C_AllocationLine al inner join C_Payment p on (p.C_Payment_ID=al.C_Payment_ID) where C_BankStatementLine_Ref.C_Payment_ID=p.C_Payment_ID) ',TO_TIMESTAMP('2015-08-27 11:48:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Zuordnungs-Position','de.metas.banking',10,'Allocation of Cash/Payment to Invoice','Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Zuordnungs-Position',0,TO_TIMESTAMP('2015-08-27 11:48:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 27.08.2015 11:48:42
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552694 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 27.08.2015 11:49:25
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552693,556276,0,329,0,TO_TIMESTAMP('2015-08-27 11:49:25','YYYY-MM-DD HH24:MI:SS'),100,'Zuordnungs-Position',0,'de.metas.swat','Allocation of Cash/Payment to Invoice',0,'Y','Y','Y','Y','N','N','N','N','N','Zuordnungs-Position',330,330,0,TO_TIMESTAMP('2015-08-27 11:49:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.08.2015 11:49:25
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556276 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 27.08.2015 11:49:57
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552694,556277,0,53074,0,TO_TIMESTAMP('2015-08-27 11:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Zuordnungs-Position',0,'de.metas.swat','Allocation of Cash/Payment to Invoice',0,'Y','Y','Y','Y','N','N','N','N','N','Zuordnungs-Position',160,160,0,TO_TIMESTAMP('2015-08-27 11:49:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.08.2015 11:49:57
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556277 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

