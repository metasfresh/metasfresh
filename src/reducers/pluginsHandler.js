import { handleAction } from 'redux-actions';

export const initialState = {
  files: [],
  components: {},
};

export default handleAction(
  'ADD-PLUGINS',
  (state, action) => {
    const plugins = action.payload;
    const files = [];
    let components = {};

    plugins.forEach(plugin => {
      if (plugin.file.components) {
        plugin.file.components.forEach(component => {
          components[`${component.id}`] = plugin.name;
        });
      }

      files.push(plugin.file);
    });

    return {
      ...state,
      files: [...state.files, ...files],
      components: { ...state.components, ...components },
    };
  },
  initialState
);
