import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IBrewCard, defaultValue } from 'app/shared/model/brew-card.model';

const initialState: EntityState<IBrewCard> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/brew-cards/user';

// Actions

// export const getbrewCards = createAsyncThunk('brewCard/fetch_entity_list', async (queryParams: IQueryParams, thunkAPI) => {
//   const result = await axios.get<IBrewCard[]>(apiUrl, { params: queryParams });
//   return result.data;
// });

export const getEntities = createAsyncThunk('brewCardUser/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IBrewCard[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'brewCardUser/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IBrewCard>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntityUser = createAsyncThunk(
  'brewCardUser/create_entity',
  async (entity: IBrewCard, thunkAPI) => {
    const result = await axios.post<IBrewCard>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntityUser = createAsyncThunk(
  'brewCardUser/update_entity',
  async (entity: IBrewCard, thunkAPI) => {
    const result = await axios.put<IBrewCard>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntityUser = createAsyncThunk(
  'brewCardUser/partial_update_entity',
  async (entity: IBrewCard, thunkAPI) => {
    const result = await axios.patch<IBrewCard>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntityUser = createAsyncThunk(
  'brewCardUser/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IBrewCard>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const BrewCardUserSlice = createEntitySlice({
  name: 'brewCardUser',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntityUser.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
        };
      })
      .addMatcher(isFulfilled(createEntityUser, updateEntityUser, partialUpdateEntityUser), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntityUser, updateEntityUser, partialUpdateEntityUser, deleteEntityUser), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = BrewCardUserSlice.actions;

// Reducer
export default BrewCardUserSlice.reducer;
