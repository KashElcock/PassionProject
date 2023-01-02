// import React, { useEffect } from 'react';
// import { Link, useParams } from 'react-router-dom';
// import { Button, Row, Col } from 'reactstrap';
// import { openFile, byteSize, TextFormat } from 'react-jhipster';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
//
// import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
// import { useAppDispatch, useAppSelector } from 'app/config/store';
//
// import { getEntityUser } from './brew-card-user.reducer';
//
// export const BrewCardUserDetail = () => {
//   const dispatch = useAppDispatch();
//
//   const { id } = useParams<'id'>();
//
//   useEffect(() => {
//     dispatch(getEntityUser(id));
//   }, []);
//
//   const brewCardEntity = useAppSelector(state => state.brewCard.entity);
//   return (
//     <Row>
//       <Col md="8">
//         <h2 data-cy="brewCardDetailsHeading">Brew Card</h2>
//         <dl className="jh-entity-details">
//           <dt>
//             <span id="id">ID</span>
//           </dt>
//           <dd>{brewCardEntity.id}</dd>
//           <dt>
//             <span id="name">Name</span>
//           </dt>
//           <dd>{brewCardEntity.name}</dd>
//           <dt>
//             <span id="brewMethod">Brew Method</span>
//           </dt>
//           <dd>{brewCardEntity.brewMethod}</dd>
//           <dt>
//             <span id="coffeeType">Coffee Type</span>
//           </dt>
//           <dd>{brewCardEntity.coffeeType}</dd>
//           <dt>
//             <span id="coffeeRegion">Coffee Region</span>
//           </dt>
//           <dd>{brewCardEntity.coffeeRegion}</dd>
//           <dt>
//             <span id="coffeeSubregion">Coffee Subregion</span>
//           </dt>
//           <dd>{brewCardEntity.coffeeSubregion}</dd>
//           <dt>
//             <span id="roastLevel">Roast Level</span>
//           </dt>
//           <dd>{brewCardEntity.roastLevel}</dd>
//           <dt>
//             <span id="processingMethod">Processing Method</span>
//           </dt>
//           <dd>{brewCardEntity.processingMethod}</dd>
//           <dt>
//             <span id="flavorProfile">Flavor Profile</span>
//           </dt>
//           <dd>{brewCardEntity.flavorProfile}</dd>
//           <dt>
//             <span id="coffeeWeight">Coffee Weight</span>
//           </dt>
//           <dd>{brewCardEntity.coffeeWeight}</dd>
//           <dt>
//             <span id="waterWeight">Water Weight</span>
//           </dt>
//           <dd>{brewCardEntity.waterWeight}</dd>
//           <dt>
//             <span id="waterTemp">Water Temp</span>
//           </dt>
//           <dd>{brewCardEntity.waterTemp}</dd>
//           <dt>
//             <span id="brewTime">Brew Time</span>
//           </dt>
//           <dd>{brewCardEntity.brewTime}</dd>
//           <dt>
//             <span id="brewDate">Brew Date</span>
//           </dt>
//           <dd>
//             {brewCardEntity.brewDate ? <TextFormat value={brewCardEntity.brewDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
//           </dd>
//           <dt>
//             <span id="equipment">Equipment</span>
//           </dt>
//           <dd>{brewCardEntity.equipment}</dd>
//           <dt>
//             <span id="notes">Notes</span>
//           </dt>
//           <dd>{brewCardEntity.notes}</dd>
//           <dt>
//             <span id="attachment">Attachment</span>
//           </dt>
//           <dd>
//             {brewCardEntity.attachment ? (
//               <div>
//                 {brewCardEntity.attachmentContentType ? (
//                   <a onClick={openFile(brewCardEntity.attachmentContentType, brewCardEntity.attachment)}>Open&nbsp;</a>
//                 ) : null}
//                 <span>
//                   {brewCardEntity.attachmentContentType}, {byteSize(brewCardEntity.attachment)}
//                 </span>
//               </div>
//             ) : null}
//           </dd>
//           <dt>User</dt>
//           <dd>{brewCardEntity.user ? brewCardEntity.user.login : ''}</dd>
//         </dl>
//         <Button tag={Link} to="/brew-card/user" replace color="info" data-cy="entityDetailsBackButton">
//           <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
//         </Button>
//         &nbsp;
//         <Button tag={Link} to={`/brew-card/user${brewCardEntity.id}/edit`} replace color="primary">
//           <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
//         </Button>
//       </Col>
//     </Row>
//   );
// };
//
// export default BrewCardUserDetail;
