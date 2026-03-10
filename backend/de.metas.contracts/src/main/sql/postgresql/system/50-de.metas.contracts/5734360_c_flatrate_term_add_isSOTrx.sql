/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Column: C_Flatrate_Term.IsSOTrx
-- 2024-09-23T13:38:34.665Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589157,1106,0,20,540320,'IsSOTrx',TO_TIMESTAMP('2024-09-23 15:38:34.47','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Dies ist eine Verkaufstransaktion','de.metas.contracts',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2024-09-23 15:38:34.47','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-23T13:38:34.677Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589157 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-23T13:38:34.683Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106)
;

-- Column: C_Flatrate_Term.IsSOTrx
-- 2024-09-23T13:38:50.770Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-23 15:38:50.77','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589157
;

-- 2024-09-23T13:38:52.138Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

UPDATE c_flatrate_term ft SET issotrx = o.issotrx
FROM (SELECT issotrx, c_order_id FROM c_order) AS o
WHERE ft.c_order_term_id = o.c_order_id;

