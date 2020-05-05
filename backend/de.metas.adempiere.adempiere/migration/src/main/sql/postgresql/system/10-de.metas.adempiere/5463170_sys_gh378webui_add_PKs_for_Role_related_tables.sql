-- 2017-05-20T12:00:11.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543344,0,'AD_Role_Included_ID',TO_TIMESTAMP('2017-05-20 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Included Role','Included Role',TO_TIMESTAMP('2017-05-20 12:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-20T12:00:11.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543344 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-05-20T12:00:12.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,556814,543344,0,13,53222,'N','AD_Role_Included_ID',TO_TIMESTAMP('2017-05-20 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Included Role',TO_TIMESTAMP('2017-05-20 12:00:11','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-05-20T12:00:12.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556814 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-05-20T12:00:12.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_Included ADD COLUMN AD_Role_Included_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_role_included_seq')
;

-- 2017-05-20T12:00:12.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_Included DROP CONSTRAINT IF EXISTS ad_role_included_pkey
;

-- 2017-05-20T12:00:12.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_Included DROP CONSTRAINT IF EXISTS ad_role_included_key
;

-- 2017-05-20T12:00:12.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_Included ADD CONSTRAINT ad_role_included_pkey PRIMARY KEY (AD_Role_Included_ID)
;

-- 2017-05-20T12:00:12.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556814,558499,0,53240,TO_TIMESTAMP('2017-05-20 12:00:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','Included Role',TO_TIMESTAMP('2017-05-20 12:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-20T12:00:12.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558499 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-05-20T12:00:35.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543345,0,'AD_Role_OrgAccess_ID',TO_TIMESTAMP('2017-05-20 12:00:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_Role_OrgAccess','AD_Role_OrgAccess',TO_TIMESTAMP('2017-05-20 12:00:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-20T12:00:35.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543345 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-05-20T12:00:36.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,556815,543345,0,13,422,'N','AD_Role_OrgAccess_ID',TO_TIMESTAMP('2017-05-20 12:00:35','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_Role_OrgAccess',TO_TIMESTAMP('2017-05-20 12:00:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2017-05-20T12:00:36.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556815 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-05-20T12:00:36.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_OrgAccess ADD COLUMN AD_Role_OrgAccess_ID numeric(10,0) NOT NULL DEFAULT nextval('ad_role_orgaccess_seq')
;

-- 2017-05-20T12:00:36.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_OrgAccess DROP CONSTRAINT IF EXISTS ad_role_orgaccess_pkey
;

-- 2017-05-20T12:00:36.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_OrgAccess DROP CONSTRAINT IF EXISTS ad_role_orgaccess_key
;

-- 2017-05-20T12:00:36.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE AD_Role_OrgAccess ADD CONSTRAINT ad_role_orgaccess_pkey PRIMARY KEY (AD_Role_OrgAccess_ID)
;

-- 2017-05-20T12:00:36.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556815,558500,0,351,TO_TIMESTAMP('2017-05-20 12:00:36','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','N','AD_Role_OrgAccess',TO_TIMESTAMP('2017-05-20 12:00:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-20T12:00:36.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558500 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

