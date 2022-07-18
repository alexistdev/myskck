<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Skck;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class SKCKController extends Controller
{
    public function tambah(Request $request)
    {
        $rules = array(
            'nik' => 'required|max:255',
            'nama' => 'required|max:255',
            'kecamatan' => 'required|max:255',
            'status' => 'required|max:255',
            'jk' => 'required|max:255',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $skck = new Skck();
            $skck->nik = $request->nik;
            $skck->nama = $request->nama;
            $skck->kecamatan = $request->kecamatan;
            $skck->status = $request->status;
            $skck->jk = $request->jk;
            $skck->save();
            return response()->json([
                'status' => true,
                'message' => 'Berhasil menyimpan data',
            ]);
        }
    }
}
