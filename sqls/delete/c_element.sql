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