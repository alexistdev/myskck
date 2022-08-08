<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Skck;
use Illuminate\Http\Request;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Exception;

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
            DB::beginTransaction();
            try {
                $skck = new Skck();
                $skck->nik = $request->nik;
                $skck->nama = $request->nama;
                $skck->kecamatan = $request->kecamatan;
                $skck->status = $request->status;
                $skck->jk = $request->jk;
                $skck->tanggal = date("Y-m-d");
                $skck->save();
                DB::commit();
                return response()->json([
                    'status' => true,
                    'message' => 'Berhasil menyimpan data',
                ], 200);
            } catch (Exception $e) {
                DB::rollback();
                return response()->json([
                    'status' => false,
                    'message' => $e->getMessage(),
                ]);
            }
        }
    }

    public function get_skck(Request $request)
    {
        $rules = array(
            'cari' => 'max:255',
            'tanggal_mulai' => 'max:255',
            'tanggal_akhir' => 'max:255',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            if (isset($request->cari)) {
               if(isset($request->tanggal_mulai) && isset($request->tanggal_akhir)){
                   $startDate  = date("Y-m-d",strtotime($request->tanggal_mulai));
                   $endDate  =date("Y-m-d",strtotime($request->tanggal_akhir));
                   $skck = Skck::where('nik', 'like', '%' . $request->cari . '%')
                       ->orWhere('nama', 'like', '%' . $request->cari . '%')
                       ->orWhere('status', 'like', '%' . $request->cari . '%')
                       ->orWhere('kecamatan', 'like', '%' . $request->cari . '%')
                       ->WhereBetween('tanggal', [$startDate, $endDate])
                       ->limit(100)->get();
               } elseif(isset($request->tanggal_mulai)) {
                    $startDate  = date("Y-m-d",strtotime($request->tanggal_mulai));
                    $skck = Skck::whereBetween('tanggal', [$startDate, $startDate])
                        ->limit(100)->get();
               } else {
                   $skck = Skck::where('nik', 'like', '%' . $request->cari . '%')
                       ->orWhere('nama', 'like', '%' . $request->cari . '%')
                       ->orWhere('status', 'like', '%' . $request->cari . '%')
                       ->orWhere('kecamatan', 'like', '%' . $request->cari . '%')
                       ->limit(100)->get();
               }
            } elseif(isset($request->tanggal_mulai)) {
                if(isset($request->tanggal_mulai) && isset($request->tanggal_akhir)){
                    $startDate  = date("Y-m-d",strtotime($request->tanggal_mulai));
                    $endDate  =date("Y-m-d",strtotime($request->tanggal_akhir));
                    $skck = Skck::whereBetween('tanggal', [$startDate, $endDate])
                        ->limit(100)->get();

                } else {
                    $startDate  =date("Y-m-d",strtotime($request->tanggal_mulai));
                    $skck = Skck::whereBetween('tanggal', [$startDate, $startDate])
                        ->limit(100)->get();

                }
            } else {
                $skck = Skck::all();
            }
            return response()->json([
                'status' => true,
                'message' => 'Berhasil menyimpan data',
                'result' => $skck,
                'jumlah' => $skck->count(),
                'cari' => $request->cari,
                'awal' => $request->tanggal_mulai,
                'akhir' => $request->tanggal_akhir,
            ], 200);
        }
    }

    public function delete_skck(Request $request)
    {
        $rules = array(
            'skck_id' => 'required|numeric',
        );
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            DB::beginTransaction();
            try {
                $skck = Skck::findOrFail($request->skck_id);
                $skck->delete();
                DB::commit();
                return response()->json([
                    'status' => true,
                    'message' => "data tidak lengkap",
                ], 200);
            } catch (Exception $e) {
                DB::rollback();
                return response()->json([
                    'status' => false,
                    'message' => $e->getMessage(),
                ]);
            }
        }
    }
}
