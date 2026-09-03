update c_bankstatementline_ref ref set c_bankstatement_id=bsl.c_bankstatement_id
from c_bankstatementline bsl
where ref.c_bankstatement_id is null
and bsl.c_bankstatementline_id=ref.c_bankstatementline_id;

