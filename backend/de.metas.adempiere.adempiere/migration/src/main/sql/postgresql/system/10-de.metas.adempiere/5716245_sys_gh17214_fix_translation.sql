
/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:08.608Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:20.758Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:23.070Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543619
;

-- Reference: PickingFilter_Options
-- Value: HandoverLocation
-- ValueName: HandoverLocation
-- 2024-01-30T23:13:35.869Z
UPDATE AD_Ref_List SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543619
;
