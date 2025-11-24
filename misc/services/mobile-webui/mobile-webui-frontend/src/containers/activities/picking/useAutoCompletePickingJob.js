import { usePickingJobCompleteStatus } from '../../../reducers/wfProcesses/picking/usePickingJobCompleteStatus';
import { useEffect, useRef } from 'react';
import { completePickingJob } from '../../../api/picking';
import { useDispatch } from 'react-redux';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import { pickingJobsListLocation } from '../../../routes/picking';

export const useAutoCompletePickingJob = ({ wfProcessId }) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const { isCompleteJobAutomatically, isFullyPicked } = usePickingJobCompleteStatus({ wfProcessId });
  const prevFullyPickedRef = useRef(false);
  const processingRef = useRef(false);

  useEffect(() => {
    if (processingRef.current || !isCompleteJobAutomatically) return;

    // Only trigger when transitioning from false -> true
    const wasFullyPicked = prevFullyPickedRef.current;
    const justBecameFullyPicked = !wasFullyPicked && isFullyPicked;

    if (justBecameFullyPicked) {
      processingRef.current = true;
      completePickingJob({ wfProcessId })
        .then(() => {
          // dispatch(updateWFProcess({ wfProcess }));
          history.goTo(pickingJobsListLocation());
        })
        .catch((err) => {
          console.warn('Auto-complete failed', err);
        })
        .finally(() => {
          processingRef.current = false;
        });
    }

    prevFullyPickedRef.current = isFullyPicked;
  }, [dispatch, history, wfProcessId, isCompleteJobAutomatically, isFullyPicked]);
};
