<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Casts\Attribute;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Carbon;

class Skck extends Model
{
    use HasFactory;

    protected $fillable =['nik','nama','kecamatan','status','jk'];
//    protected $casts = [
////        'created_at' => 'datetime:m-d-Y',
//        'created_at' => 'datetime:d-m-Y',
//    ];



    protected function nama(): Attribute
    {
        return Attribute::make(
            get: fn ($value) => ucfirst($value),
            set: fn ($value) => strtolower($value),
        );
    }



}
