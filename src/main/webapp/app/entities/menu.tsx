import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/produits">
        <Translate contentKey="global.menu.entities.produits" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vendeur">
        <Translate contentKey="global.menu.entities.vendeur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/prime">
        <Translate contentKey="global.menu.entities.prime" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/magasin">
        <Translate contentKey="global.menu.entities.magasin" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
