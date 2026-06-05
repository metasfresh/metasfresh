-- gh30044: Register KeepExistingValueAttributeSplitterStrategy and set it as the TOPD
-- splitter for the Country-of-Origin attribute on all HU PI versions.
--
-- Background: when TUs with different COO values are aggregated onto a mixed-origin LU,
-- the standard CopyAttributeSplitterStrategy overwrites every TU's COO with the LU's
-- value.  KeepExistingValueAttributeSplitterStrategy keeps a child's existing value and
-- only propagates the parent's value when the child has no value yet — preserving the
-- per-TU origin that was set during goods receipt / inventory.
--
-- IDs allocated from idserver.metas.de on 2026-06-04:
--   AD_JavaClass  540101  (KeepExistingValueAttributeSplitterStrategy)
--   AD_MigrationScript 5806320

INSERT INTO AD_JavaClass
    (AD_JavaClass_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     EntityType, IsInterface, AD_JavaClass_Type_ID, Name, Classname)
VALUES
    (540101 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'de.metas.handlingunits', 'N', 540006,
     'KeepExistingValueAttributeSplitterStrategy',
     'de.metas.handlingunits.attribute.strategy.impl.KeepExistingValueAttributeSplitterStrategy');

SELECT backup_table('m_hu_pi_attribute', '_gh30044_coo_splitter');
UPDATE M_HU_PI_Attribute
SET    SplitterStrategy_JavaClass_ID = 540101,
       Updated    = TO_TIMESTAMP('2026-06-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 99
WHERE  M_Attribute_ID  = 1000001
  AND  PropagationType = 'TOPD';
