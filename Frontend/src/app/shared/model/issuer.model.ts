export class IssuerModel {
  private _id: number;
  private _alias: string;
  private _issuer_name: string

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get alias(): string {
    return this._alias;
  }

  set alias(value: string) {
    this._alias = value;
  }

  get issuer_name(): string {
    return this._issuer_name;
  }

  set issuer_name(value: string) {
    this._issuer_name = value;
  }

  constructor(id: number, alias: string, issuer_name: string) {
    this._id = id;
    this._alias = alias;
    this._issuer_name = issuer_name;
  }
}
