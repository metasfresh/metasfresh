-- Re-show AD_Tab 546754 on window 541657 (Lieferanweisungen), bound to the INSTRUCTION instead of
-- the dropped M_ShipperTransportation.M_Delivery_Planning_ID column it used before 5820720 retired it.
--
-- Old semantics (pre-5820720): parent-linked via AD_Tab.AD_Column_ID=585629 naming the child's own
-- "M_Delivery_Planning_ID" field as the sole link column, joined against the header's (then 1:1)
-- M_Delivery_Planning_ID value, plus WhereClause "Created < @Created@" -- i.e. "earlier instructions
-- that covered THIS instruction's (single) planning". Aggregation removed the 1:1 column, so that
-- question can no longer be asked from the header; 5820720 retired the tab rather than re-point it.
--
-- New semantics (owner-approved 2026-08-27): "the delivery plannings on THIS instruction" --
-- i.e. re-parent on the instruction itself, not on a planning. Two changes, both required together:
--
-- 1) AD_Column_ID is cleared (was 585629). With AD_Column_ID set, GridTabVO.buildLinkColumnNames()
--    takes its single-column branch verbatim (the field named by that AD_Column_ID) and ignores
--    IsParent entirely -- leaving it at 585629 would silently link the reactivated tab on
--    M_Delivery_Planning_ID again (wrong: the header no longer has a single planning to compare
--    against). Clearing it makes buildLinkColumnNames() fall back to every AD_Column.IsParent='Y'
--    field, which now (5820860) includes M_ShipperTransportation_ID (585628 on the view) --
--    matching the parent's own key column name, verified via
--    GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames()'s
--    childLinkColumnNames.contains(parentLinkColumnName) branch.
-- 2) Parent_Column_ID is set to 540426 -- M_ShipperTransportation.M_ShipperTransportation_ID, the
--    OWN key column of the window's header tab (546732, table 540030) -- per
--    GridTabVOBasedDocumentEntityDescriptorFactory.extractChildParentLinkColumnNames():
--    Parent_Column_ID must resolve (parentTabVO.getColumnNameByAD_Column_ID(...)) to a column
--    belonging to the PARENT tab's own table, not the child view's. The view (AD_Table 542287)
--    exposes a same-named M_ShipperTransportation_ID column (585628), so the two sides join by name.
--
-- WhereClause is cleared (was the dead "Created < @Created@" history proxy naming a context
-- variable, @M_Delivery_Planning_ID@, the header no longer supplies). No replacement filter is
-- needed: the view's own join (M_Delivery_Planning_Alloc.IsActive='Y', since 5820700/5820820)
-- already scopes every row to the instruction's CURRENT active allocations -- exactly "the
-- plannings on this instruction". The superseded/historical allocations are intentionally NOT
-- shown here; that capability is a separate, owner-approved rebuild (plan task E5) sourced from
-- INACTIVE M_Delivery_Planning_Alloc rows, not from this tab.
UPDATE AD_Tab
   SET IsActive='Y',
       AD_Column_ID=NULL,
       Parent_Column_ID=540426,
       WhereClause=NULL,
       Updated=TO_TIMESTAMP('2026-08-27 15:05:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Tab_ID=546754
;

-- Rename: AD_Tab 546754 and AD_Tab 546737 previously carried IDENTICAL de_DE/de_CH captions
-- ("Lieferanweisungen fuer die Lieferplanung") on two DISTINCT (non-shared) AD_Elements (581962 /
-- 581926 respectively) -- a copy-paste that already caused a wrong analysis. Element 581962 is
-- used ONLY by AD_Tab 546754 (verified: no AD_Column/AD_Field/AD_Window/AD_Menu/AD_Process_Para/
-- AD_UI_Element usage), so it is safe to rename outright without forking.
UPDATE AD_Element
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung',
       Updated=TO_TIMESTAMP('2026-08-27 15:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962
;

UPDATE AD_Element_Trl
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:05:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl
   SET Name='Lieferplanungen der Lieferanweisung', Description='Lieferplanungen der Lieferanweisung', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:05:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='de_CH'
;

UPDATE AD_Element_Trl
   SET Name='Delivery Plannings for this Instruction', Description='Delivery Plannings for this Instruction', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-27 15:05:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=581962 AND AD_Language='en_US'
;

-- Propagate the base language first (also mirrors AD_Tab.Name -- update_Tab_Translation_From_AD_Element
-- writes AD_Tab itself when isBaseAD_Language, per metasfresh-application-dictionary), then the overrides.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581962, 'en_US');
