import { useEffect, useState } from 'react';
import * as api from '../api/calendar';

export const useSimulationOptimizerStatus = ({ simulationId }) => {
  const [state, setState] = useState({
    status: 'UNKNOWN ',
  });

  useEffect(() => {
    if (simulationId == null) {
      setState({ status: 'NOT_APPLICABLE' });
    } else {
      api.getSimulationOptimizerStatus({ simulationId }).then(setState);
    }
  }, [simulationId]);

  return {
    simulationId,
    status: state.status,
    setStatusFromAPIResponse: (apiResponse) => {
      setState({ status: apiResponse?.status });
    },
    setStatusFromWSEvents: (wsEvents) => {
      if (wsEvents?.simulationOptimizerStatus?.status) {
        setState({ status: wsEvents.simulationOptimizerStatus.status });
      }
    },
  };
};
