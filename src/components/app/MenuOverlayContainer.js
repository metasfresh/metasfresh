import React, { Component } from 'react';
import MenuOverlayItem from './MenuOverlayItem';

class MenuOverlayContainer extends Component {
	constructor(props){
		super(props);
	}

	render() {
		const {children, elementId, caption, type, handleClickOnFolder,handleRedirect, handleNewRedirect} = this.props;
		return (
			<div className="menu-overlay-node-container">
				{type==='group' &&
					<p className="menu-overlay-header">{caption}</p>
				}

				{type!=='group' &&

					<span
						className="menu-overlay-link"
						onClick={ e => type==='newRecord' ? handleNewRedirect(elementId) : handleRedirect(elementId)}
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
						{...subitem}
					/>
				)}
			</div>
		)
	}
}

export default MenuOverlayContainer
