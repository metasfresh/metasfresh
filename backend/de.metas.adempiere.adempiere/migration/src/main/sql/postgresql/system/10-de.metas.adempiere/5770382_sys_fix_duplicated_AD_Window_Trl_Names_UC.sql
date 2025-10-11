/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2025 metas GmbH
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


-- now create a constraint to make sure it won't happen again 
ALTER TABLE AD_Window_Trl
    ADD CONSTRAINT AD_Window_Trl_Name_UC UNIQUE (Name, AD_Language)
        DEFERRABLE INITIALLY DEFERRED;

COMMENT ON CONSTRAINT AD_Window_Trl_Name_UC ON AD_Window_Trl IS 'Needed because it''s no enough to have a UC on AD_Windows.Name. We also need to make sure that we don''t run into unique constraint errors when switching the base-language.
    
IMPORTANT:
If you create a custom window, then there is a statement like this in your SQL:
    
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) 
SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, ''N'',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,''Y''
FROM AD_Language l, AD_Window t
WHERE l.IsActive=''Y''AND (l.IsSystemLanguage=''Y'' OR l.IsBaseLanguage=''Y'') AND t.AD_Window_ID=541949 
    AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
    
...in which we insert the AD_Window_Trl records of the new custom window by copying from original window.
    
It''s important to then also have this statement, before the transaction is committed:

select update_window_translation_from_ad_element(583633);

Where in this case, 583633 is the AD_Element_ID of the custom (new) window''s AD_Window-AD_Element_ID.'
;
