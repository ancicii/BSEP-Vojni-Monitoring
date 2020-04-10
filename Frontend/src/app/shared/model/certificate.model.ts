export class CertificateModel {
  private _id: string;

  private _alias: string;

  private _end_date: Date;

  private _start_date: Date;

  private _is_active: boolean;

  private _issuer_name: string;

  private _serial_number: string;

  private _type: string;

  private _issuer_alias: string;

  get id(): string {
    return this._id;
  }

  set id(value: string) {
    this._id = value;
  }

  get alias(): string {
    return this._alias;
  }

  set alias(value: string) {
    this._alias = value;
  }

  get end_date(): Date {
    return this._end_date;
  }

  set end_date(value: Date) {
    this._end_date = value;
  }

  get start_date(): Date {
    return this._start_date;
  }

  set start_date(value: Date) {
    this._start_date = value;
  }

  get is_active(): boolean {
    return this._is_active;
  }

  set is_active(value: boolean) {
    this._is_active = value;
  }

  get issuer_name(): string {
    return this._issuer_name;
  }

  set issuer_name(value: string) {
    this._issuer_name = value;
  }

  get serial_number(): string {
    return this._serial_number;
  }

  set serial_number(value: string) {
    this._serial_number = value;
  }

  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  get issuer_alias(): string {
    return this._issuer_alias;
  }

  set issuer_alias(value: string) {
    this._issuer_alias = value;
  }

  constructor(id: string, alias: string, end_date: Date, start_date: Date, is_active: boolean, issuer_name: string, serial_number: string, type: string, issuer_alias: string) {
    this._id = id;
    this._alias = alias;
    this._end_date = end_date;
    this._start_date = start_date;
    this._is_active = is_active;
    this._issuer_name = issuer_name;
    this._serial_number = serial_number;
    this._type = type;
    this._issuer_alias = issuer_alias;
  }
}
