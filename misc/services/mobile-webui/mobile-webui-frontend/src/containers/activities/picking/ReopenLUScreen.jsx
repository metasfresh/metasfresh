import React, { useEffect, useState } from 'react';
import { useDispatch } from 'react-redux';
import Spinner from '../../../components/Spinner';
import { getClosedLUs, getHUInfoForIds, setPickTarget } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

export const ReopenLUScreen = () => {
  const { history, wfProcessId } = useScreenDefinition({
    captionKey: 'activities.picking.reopenLU',
    back: getWFProcessScreenLocation,
  });
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);
  const [closedLUs, setClosedLUs] = useState([]);

  useEffect(() => {
    setLoading(true);
    getClosedLUs({ wfProcessId })
      .then((data) => {
        if (data.huIds?.length > 0) {
          return getHUInfoForIds({ huIds: data.huIds });
        }
        return Promise.resolve({ hus: [] });
      })
      .then((data) => setClosedLUs(data?.hus ?? []))
      .finally(() => setLoading(false));
  }, [wfProcessId]);

  const onSelectedHU = (hu) => {
    const pickTarget = {
      id: hu.id,
      caption: hu.displayName,
      luId: hu.id,
    };

    setLoading(true);
    setPickTarget({ wfProcessId, target: pickTarget })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .then(() => history.goBack()) // go back to Picking Job
      .finally(() => setLoading(false));
  };

  return (
    <>
      {loading && <Spinner />}
      <SelectHUIntermediateList huList={closedLUs} onHuSelected={onSelectedHU} />
    </>
  );
};
