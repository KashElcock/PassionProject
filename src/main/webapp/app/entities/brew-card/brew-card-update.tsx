import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IBrewCard } from 'app/shared/model/brew-card.model';
import { BrewMethod } from 'app/shared/model/enumerations/brew-method.model';
import { CoffeeType } from 'app/shared/model/enumerations/coffee-type.model';
import { CoffeeRegion } from 'app/shared/model/enumerations/coffee-region.model';
import { CoffeeSubregion } from 'app/shared/model/enumerations/coffee-subregion.model';
import { RoastLevel } from 'app/shared/model/enumerations/roast-level.model';
import { ProcessingMethod } from 'app/shared/model/enumerations/processing-method.model';
import { FlavorProfile } from 'app/shared/model/enumerations/flavor-profile.model';
import { getEntity, updateEntity, createEntity, reset } from './brew-card.reducer';

export const BrewCardUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const brewCardEntity = useAppSelector(state => state.brewCard.entity);
  const loading = useAppSelector(state => state.brewCard.loading);
  const updating = useAppSelector(state => state.brewCard.updating);
  const updateSuccess = useAppSelector(state => state.brewCard.updateSuccess);
  const brewMethodValues = Object.keys(BrewMethod);
  const coffeeTypeValues = Object.keys(CoffeeType);
  const coffeeRegionValues = Object.keys(CoffeeRegion);
  const coffeeSubregionValues = Object.keys(CoffeeSubregion);
  const roastLevelValues = Object.keys(RoastLevel);
  const processingMethodValues = Object.keys(ProcessingMethod);
  const flavorProfileValues = Object.keys(FlavorProfile);

  const handleClose = () => {
    navigate('/brew-card');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...brewCardEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          brewMethod: 'POUR_OVER',
          coffeeType: 'ARABICA',
          coffeeRegion: 'AFRICA',
          coffeeSubregion: 'ETHIOPIA',
          roastLevel: 'LIGHT',
          processingMethod: 'WASHED',
          flavorProfile: 'FRUITY',
          ...brewCardEntity,
          user: brewCardEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="passionprojectApp.brewCard.home.createOrEditLabel" data-cy="BrewCardCreateUpdateHeading">
            Create or edit a Brew Card
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="brew-card-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="brew-card-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Brew Method" id="brew-card-brewMethod" name="brewMethod" data-cy="brewMethod" type="select">
                {brewMethodValues.map(brewMethod => (
                  <option value={brewMethod} key={brewMethod}>
                    {brewMethod}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Coffee Type" id="brew-card-coffeeType" name="coffeeType" data-cy="coffeeType" type="select">
                {coffeeTypeValues.map(coffeeType => (
                  <option value={coffeeType} key={coffeeType}>
                    {coffeeType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Coffee Region" id="brew-card-coffeeRegion" name="coffeeRegion" data-cy="coffeeRegion" type="select">
                {coffeeRegionValues.map(coffeeRegion => (
                  <option value={coffeeRegion} key={coffeeRegion}>
                    {coffeeRegion}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Coffee Subregion"
                id="brew-card-coffeeSubregion"
                name="coffeeSubregion"
                data-cy="coffeeSubregion"
                type="select"
              >
                {coffeeSubregionValues.map(coffeeSubregion => (
                  <option value={coffeeSubregion} key={coffeeSubregion}>
                    {coffeeSubregion}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Roast Level" id="brew-card-roastLevel" name="roastLevel" data-cy="roastLevel" type="select">
                {roastLevelValues.map(roastLevel => (
                  <option value={roastLevel} key={roastLevel}>
                    {roastLevel}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Processing Method"
                id="brew-card-processingMethod"
                name="processingMethod"
                data-cy="processingMethod"
                type="select"
              >
                {processingMethodValues.map(processingMethod => (
                  <option value={processingMethod} key={processingMethod}>
                    {processingMethod}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Flavor Profile"
                id="brew-card-flavorProfile"
                name="flavorProfile"
                data-cy="flavorProfile"
                type="select"
              >
                {flavorProfileValues.map(flavorProfile => (
                  <option value={flavorProfile} key={flavorProfile}>
                    {flavorProfile}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Coffee Weight"
                id="brew-card-coffeeWeight"
                name="coffeeWeight"
                data-cy="coffeeWeight"
                type="text"
                validate={{
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Water Weight"
                id="brew-card-waterWeight"
                name="waterWeight"
                data-cy="waterWeight"
                type="text"
                validate={{
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Water Temp"
                id="brew-card-waterTemp"
                name="waterTemp"
                data-cy="waterTemp"
                type="text"
                validate={{
                  min: { value: 0, message: 'This field should be at least 0.' },
                  max: { value: 200, message: 'This field cannot be more than 200.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Brew Time"
                id="brew-card-brewTime"
                name="brewTime"
                data-cy="brewTime"
                type="text"
                validate={{
                  min: { value: 0, message: 'This field should be at least 0.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Brew Date" id="brew-card-brewDate" name="brewDate" data-cy="brewDate" type="date" />
              <ValidatedField label="Equipment" id="brew-card-equipment" name="equipment" data-cy="equipment" type="text" />
              <ValidatedField label="Notes" id="brew-card-notes" name="notes" data-cy="notes" type="text" />
              <ValidatedBlobField
                label="Attachment"
                id="brew-card-attachment"
                name="attachment"
                data-cy="attachment"
                openActionLabel="Open"
              />
              <ValidatedField id="brew-card-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/brew-card" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BrewCardUpdate;
