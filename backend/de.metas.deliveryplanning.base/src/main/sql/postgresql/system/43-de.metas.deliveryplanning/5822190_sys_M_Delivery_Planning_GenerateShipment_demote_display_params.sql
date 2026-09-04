-- Task Q12: doIt() of M_Delivery_Planning_GenerateShipment never reads QtyTotalOpen or QtyAvailableParam -
-- both are display-only aids for the user typing the Qty override, so neither should be mandatory.

-- QtyAvailableParam (542644) is already read-only (ReadOnlyLogic='1=1') but was still mandatory, which -
-- combined with the @Param binding bug fixed in the same commit (it was never bound to a value) - meant
-- the field could never be satisfied. Demote to optional.
UPDATE AD_Process_Para SET IsMandatory='N', Updated=TO_TIMESTAMP('2026-09-03 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=542644 /*QtyAvailableParam*/
;

-- QtyTotalOpen has no AD_Process_Para at all, yet the Java field backing it (p_QtyToDeliverBD) carried
-- @Param(mandatory=true) - the mandatory check runs from the annotation alone (ProcessClassParamInfo),
-- independent of AD_Process_Para even existing, so M_Delivery_Planning_GenerateShipment could never
-- complete its own prepare() step: FillMandatoryException("QtyTotalOpen") fired on every single run,
-- because no AD_Process_Para meant no way to ever supply a value. Add it as optional, read-only display -
-- the same shape as QtyAvailableParam - reusing the M_Delivery_Planning.QtyTotalOpen element (581682, "Offene
-- Menge (geliefert)") rather than minting a duplicate.
INSERT INTO AD_Process_Para (
    AD_Process_Para_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, AD_Process_ID, SeqNo, AD_Reference_ID, ColumnName, IsCentrallyMaintained, FieldLength,
    IsMandatory, IsRange, EntityType, IsEncrypted, ShowInactiveValues, AD_Element_ID, ReadOnlyLogic)
SELECT
    543297 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-09-03 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-09-03 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
    e.Name, p.AD_Process_ID, 21, 29 /*Quantity*/, 'QtyTotalOpen', 'Y', 0,
    'N', 'N', 'D', 'N', 'N', e.AD_Element_ID, '1=1'
FROM AD_Process p, AD_Element e
WHERE p.Value = 'M_Delivery_Planning_GenerateShipment'
  AND e.AD_Element_ID = 581682 /*QtyTotalOpen*/
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para pp WHERE pp.AD_Process_ID = p.AD_Process_ID AND pp.ColumnName = 'QtyTotalOpen')
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Name, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Process_Para_ID = 543297
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

/* DDL */  select update_Process_Para_Translation_From_AD_Element(581682)
;
