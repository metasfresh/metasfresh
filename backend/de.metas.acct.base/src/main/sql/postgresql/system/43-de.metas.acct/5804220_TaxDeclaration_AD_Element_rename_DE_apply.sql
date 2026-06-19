-- Tax Declaration — apply the AD_Element renames intended by 5804170
-- Follow-up because 5804170's first version updated AD_Element directly + only en_US Trl;
-- the cascade's update_ad_element_on_ad_element_trl_update step then re-synced
-- AD_Element.Name from AD_Element_Trl[de_DE] (which still held English), reverting the rename.
-- Now using the documented metasfresh-application-dictionary pattern:
-- always update AD_Element_Trl (de_DE / de_CH / en_US), then run the cascade.
-- The corrected 5804170 makes this redundant on fresh applies; this script exists so DBs
-- that already saw the broken 5804170 still get the rename (idempotent UPDATEs).

-- AD_Element 2863 — C_TaxDeclarationLine_ID
UPDATE AD_Element_Trl SET Name='Steuererklärungsposition', PrintName='Steuererklärungsposition',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:01', UpdatedBy=100
WHERE AD_Element_ID=2863 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Steuererklärungsposition', PrintName='Steuererklärungsposition',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:02', UpdatedBy=100
WHERE AD_Element_ID=2863 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Tax Declaration Line', PrintName='Tax Declaration Line',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:03', UpdatedBy=100
WHERE AD_Element_ID=2863 AND AD_Language='en_US';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2863, NULL);

-- AD_Element 2864 — C_TaxDeclarationAcct_ID
UPDATE AD_Element_Trl SET Name='Steuererklärungs-Kontierung', PrintName='Steuererklärungs-Kontierung',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:04', UpdatedBy=100
WHERE AD_Element_ID=2864 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Steuererklärungs-Kontierung', PrintName='Steuererklärungs-Kontierung',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:05', UpdatedBy=100
WHERE AD_Element_ID=2864 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Tax Declaration Accounting', PrintName='Tax Declaration Accounting',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:06', UpdatedBy=100
WHERE AD_Element_ID=2864 AND AD_Language='en_US';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2864, NULL);

-- AD_Element 584856 — LineCount
UPDATE AD_Element_Trl SET Name='Positionsanzahl', PrintName='Positionsanzahl',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:07', UpdatedBy=100
WHERE AD_Element_ID=584856 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Positionsanzahl', PrintName='Positionsanzahl',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:08', UpdatedBy=100
WHERE AD_Element_ID=584856 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Line Count', PrintName='Line Count',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:09', UpdatedBy=100
WHERE AD_Element_ID=584856 AND AD_Language='en_US';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584856, NULL);

-- AD_Element 584857 — C_TaxDeclaration_Legacy
UPDATE AD_Element_Trl SET Name='Steuererklärung (alt)', PrintName='Steuererklärung (alt)',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:10', UpdatedBy=100
WHERE AD_Element_ID=584857 AND AD_Language='de_DE';
UPDATE AD_Element_Trl SET Name='Steuererklärung (alt)', PrintName='Steuererklärung (alt)',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:11', UpdatedBy=100
WHERE AD_Element_ID=584857 AND AD_Language='de_CH';
UPDATE AD_Element_Trl SET Name='Tax Declaration (legacy)', PrintName='Tax Declaration (legacy)',
    IsTranslated='Y', Updated=TIMESTAMP '2026-05-22 14:00:12', UpdatedBy=100
WHERE AD_Element_ID=584857 AND AD_Language='en_US';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584857, NULL);
