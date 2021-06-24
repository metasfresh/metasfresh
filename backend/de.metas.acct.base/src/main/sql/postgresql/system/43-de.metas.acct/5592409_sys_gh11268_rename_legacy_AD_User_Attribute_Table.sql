UPDATE ad_table
SET tableName='AD_User_Attribute_legacy', technicalnote='metas-ts: This table existed as AD_User_Attribute in instances, but i did not find it''s SQL. ' ||
                                                        'Now there was a collision with 43-de.metas.acct/5592420_sys_gh11268_UserAttributeListAndTab.sql,' ||
                                                        'So I''m renaming it to AD_User_Attribute_legacy.'
  , updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE tablename = 'AD_User_Attribute'
;

DO
$$
    BEGIN
        ALTER TABLE AD_User_Attribute
            RENAME TO AD_User_Attribute_legacy;
    EXCEPTION
        WHEN OTHERS THEN RAISE NOTICE 'ALTER TABLE AD_User_Attribute failed; probably it does not yet exist';
    END
$$
;

DO
$$
    BEGIN
        ALTER TABLE AD_User_Attribute_legacy
            RENAME COLUMN AD_User_Attribute_id TO AD_User_Attribute_legacy_id;
    EXCEPTION
        WHEN OTHERS THEN RAISE NOTICE 'ALTER TABLE AD_User_Attribute_legacy failed; probably it does not yet exist';
    END
$$
;

UPDATE ad_element
SET columnname='AD_User_Attribute_legacy_ID', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE columnname = 'AD_User_Attribute_ID'
;
;

UPDATE ad_column
SET columnname='AD_User_Attribute_legacy_ID', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE ad_element_id = (SELECT ad_element_id FROM ad_element WHERE columnname = 'AD_User_Attribute_legacy_ID')
;
;

UPDATE AD_Sequence
SET Name='AD_User_Attribute_legacy', description='Table AD_User_Attribute_legacy', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE Name = 'AD_User_Attribute'
;

DO
$$
    BEGIN
        ALTER SEQUENCE AD_USER_ATTRIBUTE_SEQ RENAME TO AD_USER_ATTRIBUTE_LEGACY_SEQ;
    EXCEPTION
        WHEN OTHERS THEN RAISE NOTICE 'ALTER SEQUENCE AD_USER_ATTRIBUTE_SEQ failed; probably it does not yet exist';
    END;
$$
;

DO
$$
    BEGIN
        ALTER TABLE ad_user_attribute_legacy
            DROP CONSTRAINT ad_user_attribute_key;
    EXCEPTION
        WHEN OTHERS THEN RAISE NOTICE 'ALTER TABLE ad_user_attribute_legacy failed; probably it does not yet exist';
    END
$$
;

DO
$$
    BEGIN
        ALTER TABLE ad_user_attribute_legacy
            ADD CONSTRAINT ad_user_attribute_legacy_key
                PRIMARY KEY (ad_user_attribute_legacy_id);
    EXCEPTION
        WHEN OTHERS THEN RAISE NOTICE 'ALTER TABLE ad_user_attribute_legacy failed; probably it does not yet exist';
    END
$$
;

