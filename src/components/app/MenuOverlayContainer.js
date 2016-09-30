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
		const {children, elementId, caption, type, handleClickOnFolder,handleRedirect, handleNewRedirect, handlePath} = this.props;
		return (
			<div className="menu-overlay-node-container">
				{type==='group' &&
					<p className="menu-overlay-header">{caption}</p>
				}

				{type!=='group' &&

					<span
						className="menu-overlay-link"
						onClick={ e => type==='newRecord' ? handleNewRedirect(elementId) : this.handleClick()}
					>
						{caption}
					</span>

				
				}

				{children && children.map((subitem, subindex) =>
					<MenuOverlayItem
						key={subindex}
						handleClickOnFolder={handleClickOnFolder}
						handleRedirect={handleRedirect}
						handleNewRedirect={handleNewRedirect}
						handlePath={handlePath}
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
