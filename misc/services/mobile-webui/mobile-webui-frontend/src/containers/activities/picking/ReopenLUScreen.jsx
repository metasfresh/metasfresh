import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { trl } from '../../../utils/translations';
import { useEffect, useState } from 'react';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import Spinner from '../../../components/Spinner';
import { getClosedLUs, getHUInfoForIds, setPickTarget } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';

export const ReopenLUScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const {
    url,
    params: { workflowId: wfProcessId },
  } = useRouteMatch();
  const [loading, setLoading] = useState(false);
  const [closedLUs, setClosedLUs] = useState([]);

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.picking.reopenLU'),
      })
    );
  }, []);

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
      .then(() => history.go(-1)) // go back to Picking Job
      .finally(() => setLoading(false));
  };

  return (
    <>
      {loading && <Spinner />}
      <SelectHUIntermediateList huList={closedLUs} onHuSelected={onSelectedHU} />
    </>
  );
};
