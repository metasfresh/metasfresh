import React from 'react';
import { useSelector } from 'react-redux';
import YesNoDialog from '../../components/dialogs/YesNoDialog';
import { resolvePendingShelfLife, selectPendingShelfLifeConfirmation } from './redux/pickingUiSlice';

/**
 * Renders the shelf-life (RLZ) confirmation dialog when the server returns
 * an RLZ_TooShort error during a pick. Driven by redux state set by
 * postStepPickedThunk — no imperative DOM mounting.
 *
 * Mount this once inside the picking activity render tree so it is present
 * for both the manual qty-dialog pick path and the auto-pick fast path.
 */
const ShelfLifeConfirmDialogHost = () => {
  const pending = useSelector(selectPendingShelfLifeConfirmation);

  if (!pending) {
    return null;
  }

  return (
    <YesNoDialog
      promptQuestion={pending.message}
      onYes={() => resolvePendingShelfLife(true)}
      onNo={() => resolvePendingShelfLife(false)}
    />
  );
};

export default ShelfLifeConfirmDialogHost;
