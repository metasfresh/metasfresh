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
            children, elementId, caption, type, handleClickOnFolder, handleRedirect,
            handleNewRedirect, handlePath, printChildren, deep, back, handleMenuOverlay,
            openModal
        } = this.props;

		return (
			<div
                tabIndex={0}
                className={
                    "menu-overlay-node-container " +
                    (deep ? "menu-overlay-node-spaced " : "menu-overlay-expanded-link-spaced")
                }>
				{type === 'group' &&
					<span
                        className={
                            "menu-overlay-header " +
                            (!printChildren ? "menu-overlay-header-spaced " : " ") +
                            (!deep ? "menu-overlay-header-main" : " ")
                        }
                    >{caption}</span>
                }
                {type !== 'group' &&
                    <MenuOverlayItem
                        elementId={elementId}
                        caption={caption}
                        type={type}
                        handleClickOnFolder={handleClickOnFolder}
                        handleRedirect={handleRedirect}
                        handleNewRedirect={handleNewRedirect}
                        handlePath={handlePath}
                        back={back}
                        handleMenuOverlay={handleMenuOverlay}
                        printChildren={false}
                        openModal={openModal}
                    />

				}

				{children && children.map((subitem, subindex) =>
					subitem.children && printChildren ?
                        <MenuOverlayContainer
                            key={subindex}
                            handleClickOnFolder={handleClickOnFolder}
                            handleRedirect={handleRedirect}
                            handleNewRedirect={handleNewRedirect}
                            printChildren={true}
                            deep={true}
                            openModal={openModal}
                            {...subitem}
    					/> :
                        <MenuOverlayItem
    						key={subindex}
    						handleClickOnFolder={handleClickOnFolder}
    						handleRedirect={handleRedirect}
    						handleNewRedirect={handleNewRedirect}
    						handlePath={handlePath}
                            printChildren={printChildren}
                            back={back}
                            handleMenuOverlay={handleMenuOverlay}
                            openModal={openModal}
    						{...subitem}
                        />
				)}
			</div>
		)
	}
}

function mapStateToProps(state) {
    const { windowHandler, menuHandler } = state;
    const {
        master,
        connectionError,
        modal
    } = windowHandler || {
        master: {},
        connectionError: false,
        modal: false
    }


    const {
        breadcrumb
    } = menuHandler || {
        breadcrumb: {}
    }

    return {
        master,
        connectionError,
        breadcrumb,
        modal
    }
}

MenuOverlayContainer = connect(mapStateToProps)(MenuOverlayContainer);

export default MenuOverlayContainer
