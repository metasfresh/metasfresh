DROP VIEW RV_Asset_Customer
;

CREATE OR REPLACE VIEW RV_Asset_Customer AS
SELECT a.a_asset_id,
       a.ad_client_id,
       a.ad_org_id,
       a.isactive,
       a.created,
       a.createdby,
       a.updated,
       a.updatedby,
       a.value,
       a.name,
       a.description,
       a.help,
       a.a_asset_group_id,
       a.m_product_id,
       a.versionno,
       a.assetservicedate,
       a.c_bpartner_id,
       a.c_bpartner_location_id,
       a.ad_user_id,
       (SELECT COUNT(*) AS count
        FROM a_asset_delivery ad
        WHERE a.a_asset_id = ad.a_asset_id) AS deliverycount
FROM a_asset a
WHERE a.c_bpartner_id IS NOT NULL;