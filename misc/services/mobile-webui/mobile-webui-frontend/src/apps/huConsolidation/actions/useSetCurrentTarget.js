import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';

export const useSetCurrentTarget = ({ wfProcessId }) => {
  const dispatch = useDispatch();

  return ({ target }) => {
    return api.setTarget({ wfProcessId, target }).then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
  };
};
