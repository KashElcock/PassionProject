import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { BrewMethod } from 'app/shared/model/enumerations/brew-method.model';
import { CoffeeType } from 'app/shared/model/enumerations/coffee-type.model';
import { CoffeeRegion } from 'app/shared/model/enumerations/coffee-region.model';
import { CoffeeSubregion } from 'app/shared/model/enumerations/coffee-subregion.model';
import { RoastLevel } from 'app/shared/model/enumerations/roast-level.model';
import { ProcessingMethod } from 'app/shared/model/enumerations/processing-method.model';
import { FlavorProfile } from 'app/shared/model/enumerations/flavor-profile.model';

export interface IBrewCard {
  id?: number;
  name?: string | null;
  brewMethod?: BrewMethod | null;
  coffeeType?: CoffeeType | null;
  coffeeRegion?: CoffeeRegion | null;
  coffeeSubregion?: CoffeeSubregion | null;
  roastLevel?: RoastLevel | null;
  processingMethod?: ProcessingMethod | null;
  flavorProfile?: FlavorProfile | null;
  coffeeWeight?: number | null;
  waterWeight?: number | null;
  waterTemp?: number | null;
  brewTime?: number | null;
  brewDate?: string | null;
  equipment?: string | null;
  notes?: string | null;
  attachmentContentType?: string | null;
  attachment?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IBrewCard> = {};
