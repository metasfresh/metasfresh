-- nShift: add CountryOfOrigin attribute to Virtual PI (101) so it appears in the HU editor
-- on direct-VHU receipts (no TU intermediate).
-- Without this, the CountryOfOrigin field is absent from VHU-direct receipt dialogs
-- because M_HU_PI_Attribute was only on PI 100 (Template TU) with TOPD propagation.
-- PropagationType=NONE: VHU is the bottom level, no further propagation needed.

-- IDs allocated from idserver.metas.de on 2026-06-02:
--   M_HU_PI_Attribute  540148  (Virtual PI 101 × CountryOfOrigin attribute 1000001)

INSERT INTO M_HU_PI_Attribute
    (M_HU_PI_Attribute_ID, AD_Client_ID, AD_Org_ID,
     M_HU_PI_Version_ID, M_Attribute_ID,
     IsActive, IsInstanceAttribute, IsMandatory, IsReadOnly, UseInASI, IsDisplayed,
     PropagationType, HU_TansferStrategy_JavaClass_ID, SeqNo,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (540148 /*From ID Server*/, 0, 0,
     101, 1000001,
     'Y', 'Y', 'N', 'N', 'Y', 'Y',
     'NONE', 540027, 60,
     TO_TIMESTAMP('2026-06-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
