select assertEmpty('select * from get_db_columns_LogicExpression() where logicExpression is not null and logicExpression ilike ''%^%''  ');
select assertEmpty('select * from get_db_columns_LogicExpression() where logicExpression is not null and logicExpression ilike ''%~%''  ');


