import {
  POS_TERMINAL_CLOSING,
  POS_TERMINAL_CLOSING_CANCEL,
  POS_TERMINAL_LOAD_DONE,
  POS_TERMINAL_LOAD_START,
  TOGGLE_PRODUCT_CATEGORY_FILTER,
} from '../actionTypes';

export function posTerminalReducer(applicationState, action) {
  switch (action.type) {
    case POS_TERMINAL_LOAD_START: {
      return updatePOSTerminalToState({
        applicationState,
        props: { isLoading: true },
      });
    }
    case POS_TERMINAL_LOAD_DONE: {
      const { posTerminal } = action.payload;
      return setPOSTerminalToState({
        applicationState,
        newTerminal: posTerminal,
      });
    }
    case POS_TERMINAL_CLOSING: {
      return updatePOSTerminalToState({
        applicationState,
        props: (terminal) => ({
          isCashJournalClosing: !!terminal.cashJournalOpen, // closing makes sense only if the journal is already open
        }),
      });
    }
    case POS_TERMINAL_CLOSING_CANCEL: {
      return updatePOSTerminalToState({
        applicationState,
        props: {
          isCashJournalClosing: false,
        },
      });
    }
    case TOGGLE_PRODUCT_CATEGORY_FILTER: {
      const { categoryId } = action.payload;
      return toggleProductCategoryFilter({ applicationState, categoryId });
    }
  }

  return applicationState;
}

//
//
//
//
//
//
//
//
//

const updatePOSTerminalToState = ({ applicationState, props }) => {
  const currentTerminal = applicationState.terminal ?? {};

  let newProps;
  if (typeof props === 'function') {
    newProps = props(currentTerminal);
  } else {
    newProps = props;
  }

  if (!newProps) return applicationState;

  return {
    ...applicationState,
    terminal: {
      ...currentTerminal,
      ...newProps,
    },
  };
};

const setPOSTerminalToState = ({ applicationState, newTerminal }) => {
  // preserve closing flag.
  // also, closing makes sense only if the journal is already open.
  const currentTerminal = applicationState?.terminal ?? {};
  const isCashJournalClosing =
    currentTerminal.isCashJournalClosing && newTerminal?.cashJournalOpen && newTerminal.id === currentTerminal.id;

  const products = newTerminal?.products ? newTerminal.products : currentTerminal.products ?? {};
  const productCategories = newTerminal?.productCategories
    ? newTerminal.productCategories
    : currentTerminal.productCategories ?? [];

  const newTerminalEffective = {
    ...newTerminal,
    isCashJournalClosing,
    isLoading: false,
    isLoaded: true,
    products,
    productCategories,
  };
  delete newTerminalEffective.openOrders;

  return {
    ...applicationState,
    terminal: newTerminalEffective,
  };
};

const toggleProductCategoryFilter = ({ applicationState, categoryId }) => {
  return updatePOSTerminalToState({
    applicationState,
    props: (terminal) => {
      if (!terminal.productCategories) return;

      const productCategories = terminal.productCategories.map((category) => {
        if (category.id === categoryId) {
          return {
            ...category,
            selected: !category.selected,
          };
        } else if (category.selected) {
          return {
            ...category,
            selected: false,
          };
        } else {
          return category;
        }
      });

      return { productCategories };
    },
  });
};
