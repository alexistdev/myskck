<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\SKCKController as skck;


Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
Route::get('/skck', [skck::class, 'get_skck'])->name('api.skck');
Route::post('/skck', [skck::class, 'tambah'])->name('api.tambah');
Route::delete('/skck', [skck::class, 'delete_skck'])->name('api.delete');

