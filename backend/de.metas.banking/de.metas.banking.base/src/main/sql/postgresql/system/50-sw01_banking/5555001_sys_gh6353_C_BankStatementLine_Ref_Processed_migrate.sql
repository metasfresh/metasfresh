update c_bankstatementline_ref ref set processed=bs.processed
from c_bankstatement bs
where ref.c_bankstatement_id=bs.c_bankstatement_id;

