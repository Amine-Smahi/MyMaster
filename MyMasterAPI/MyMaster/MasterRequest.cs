using System.ComponentModel.DataAnnotations.Schema;

namespace MyMaster
{
  public class MasterRequest
  {
    [Column("3")] public float AlgoScore;

    [Column("5")] public float CliScore;

    [Column("4")] public float CodingScore;

    [Column("1")] public float DesignScore;

    [Column("7")] public float HardwareScore;

    [Column("0")] public float Label;

    [Column("2")] public float MathScore;

    [Column("6")] public float NetworkingScore;

    [Column("8")] public int type;
  }
}