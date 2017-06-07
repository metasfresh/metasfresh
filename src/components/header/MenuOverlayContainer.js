import React, { Component } from 'react';
import MenuOverlayItem from './MenuOverlayItem';
import {connect} from 'react-redux';

import {
    getWindowBreadcrumb
} from '../../actions/MenuActions';

class MenuOverlayContainer extends Component {
    constructor(props){
        super(props);
    }

    handleClick = () => {
        const {dispatch, handleRedirect, elementId} = this.props;
        handleRedirect(elementId);
        dispatch(getWindowBreadcrumb(elementId));
    }

    render() {
        const {
            children, elementId, caption, type, handleClickOnFolder,
            handleRedirect, handleNewRedirect, handlePath, printChildren, deep,
            back, handleMenuOverlay, openModal, showBookmarks
        } = this.props;

        return (
            <div
                tabIndex={0}
                className={
                    'menu-overlay-node-container ' +
                    (deep ? 'menu-overlay-node-spaced ' :
                        'menu-overlay-expanded-link-spaced')
                }>
                {type === 'group' &&
                    <span
                        className={
                            'menu-overlay-header ' +
                            (!printChildren ?
                                'menu-overlay-header-spaced ' : ' ') +
                            (!deep ? 'menu-overlay-header-main' : ' ')
                        }
                        >{caption}</span>
                }
                {type !== 'group' &&
                    <MenuOverlayItem
                        printChildren={false}
                        {...{showBookmarks, openModal, handleMenuOverlay,
                            handlePath, back, handleNewRedirect, handleRedirect,
                            handleClickOnFolder, type, caption, elementId
                        }}
                    />
                }

                {children && children.map((subitem, subindex) =>
                    subitem.children && printChildren ?
                        <MenuOverlayContainer
                            key={subindex}
                            printChildren={true}
                            deep={true}
                            {...subitem}
                            {...{showBookmarks, openModal, handleNewRedirect,
                                handleRedirect, handleClickOnFolder}}
                        /> :
                        <MenuOverlayItem
                            key={subindex}
                            {...subitem}
                            {...{showBookmarks, handleMenuOverlay, openModal,
                                back, printChildren, handlePath,
                                handleNewRedirect, handleRedirect,
                                handleClickOnFolder
                            }}
                        />
                )}
            </div>
        )
    }
}

MenuOverlayContainer = connect()(MenuOverlayContainer);

export default MenuOverlayContainer
