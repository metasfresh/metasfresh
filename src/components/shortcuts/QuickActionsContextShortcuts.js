import React, { Component } from 'react';
import { Shortcuts } from 'react-shortcuts';

/* TODO: Refactor this hack
 * https://github.com/metasfresh/metasfresh-webui-frontend/issues/1283
 */
let componentHierarchy = [];

class QuickActionsContextShortcuts extends Component {
    componentWillMount() {
        // Rerender components lower in the hierarchy when a new one is added
        const _componentHierarchy = componentHierarchy;
        componentHierarchy = [...componentHierarchy, this];

        for (const component of _componentHierarchy) {
            component.forceUpdate();
        }
    }

    componentWillUnmount() {
        componentHierarchy = componentHierarchy.filter(
            component => component !== this
        );
    }

    handleShortcuts = (action, event) => {
        const {handleClick, onClick} = this.props;

        switch (action) {
        case 'QUICK_ACTION_POS':
            event.preventDefault();
            handleClick();
            break
        case 'QUICK_ACTION_TOGGLE':
            event.preventDefault();
            onClick();
            break
        }
    }

    render() {
        // Only render the top most component in the hierarchy
        if (componentHierarchy[componentHierarchy.length - 1] !== this) {
            return null;
        }

        return (
            <Shortcuts
                name="QUICK_ACTIONS"
                handler={this.handleShortcuts}
                targetNodeSelector = "body"
                isolate
                global
                preventDefault
                stopPropagation
                alwaysFireHandler
            />
        );
    }
}

export default QuickActionsContextShortcuts;
