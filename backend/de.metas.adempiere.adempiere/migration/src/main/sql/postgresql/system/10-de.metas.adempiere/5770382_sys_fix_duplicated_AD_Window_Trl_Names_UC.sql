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

COMMENT ON CONSTRAINT AD_Window_Trl_Name_UC ON AD_Window_Trl IS 'Needed because It''s no enough to have a UC on AD_Windows.Name. We also need to make sure that we don''t run into unique constraint errors when switching the base-language';
