delete
from AD_TreeNode
where ad_tree_id = 1000040;
delete
from ad_tree
where ad_tree_id = 1000040;

delete
from C_ValidCombination
where exists
          (
              select 1
              from c_elementvalue ev
              where ev.c_elementvalue_id = C_ValidCombination.Account_ID
                and ev.c_element_id = 1000003
          );

delete from c_elementvalue
where c_element_id = 1000003;

delete from c_element
where c_element_id = 1000003;


--- insert valid combination data


INSERT INTO c_validcombination (c_validcombination_id, ad_client_id, ad_org_id, isactive, created, createdby, updated,
                                updatedby, alias, combination, description, isfullyqualified, c_acctschema_id,
                                account_id, m_product_id, c_bpartner_id, ad_orgtrx_id, c_locfrom_id, c_locto_id,
                                c_salesregion_id, c_project_id, c_campaign_id, c_activity_id, user1_id, user2_id,
                                c_subacct_id, userelement1_id, userelement2_id)
select nextval('c_validcombination_seq'),
       1000000,
       ad_org_id,
       'Y',
       now(),
       99,
       now(),
       99,
       null,
       value,
       name,
       'Y',
       1000000,
       c_elementvalue_id,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null,
       null
from c_elementvalue
where c_elementvalue_id = 1003500;


